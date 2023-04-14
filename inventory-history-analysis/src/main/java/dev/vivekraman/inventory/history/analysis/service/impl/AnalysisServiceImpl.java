package dev.vivekraman.inventory.history.analysis.service.impl;

import dev.vivekraman.inventory.history.analysis.config.InventoryHistoryAnalysisProperties;
import dev.vivekraman.task.MDCPropogatedTask;
import dev.vivekraman.inventory.history.analysis.engine.Rule;
import dev.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import dev.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;
import dev.vivekraman.inventory.history.analysis.repository.WarehouseInventoryHistoryTransactionRepository;
import dev.vivekraman.inventory.history.analysis.service.api.AnalysisJobService;
import dev.vivekraman.inventory.history.analysis.service.api.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService, InitializingBean,
    ApplicationContextAware {
  private final AnalysisJobService analysisJobService;
  private final WarehouseInventoryHistoryTransactionRepository txnRepository;
  private final InventoryHistoryAnalysisProperties props;
  private final ExecutorService threadPoolExecutorService;
  private final ExecutorService executorService;

  @Autowired
  public AnalysisServiceImpl(AnalysisJobService analysisJobService,
      WarehouseInventoryHistoryTransactionRepository txnRepository,
      InventoryHistoryAnalysisProperties props,
      @Qualifier("inventoryHistoryAnalysisThreadPoolExecutorService")
      ExecutorService threadPoolExecutorService,
      ExecutorService executorService) {
    this.analysisJobService = analysisJobService;
    this.txnRepository = txnRepository;
    this.props = props;
    this.threadPoolExecutorService = threadPoolExecutorService;
    this.executorService = executorService;
  }

  private ApplicationContext applicationContext;
  private List<Rule> rules = new ArrayList<>();

  @Override
  public void afterPropertiesSet() throws Exception {
    this.rules = applicationContext.getBeansOfType(Rule.class).values().stream()
        .filter(Rule::isActive)
        .toList();
  }

  @Override
  public void analyzeAsync(AnalysisJob job, String inventoryIdentifier) {
    this.executorService.execute(new MDCPropogatedTask() {
      @Override
      public void execute() {
        analyze(job, inventoryIdentifier);
      }
    });
  }

  @Override
  public void analyze(AnalysisJob job, String inventoryIdentifier) {
    Pageable pageRequest = PageRequest.of(0, props.getAnalysisBatchSize());

    Page<WarehouseInventoryHistoryTransaction> page =
        this.txnRepository.findByInventoryIdentifierOrderByTransactionDate(
            inventoryIdentifier, pageRequest);

    AtomicInteger txnProcessedCount = new AtomicInteger(0);
    do {
      List<CompletableFuture<Boolean>> futures =  new ArrayList<>(page.getSize());
      for (WarehouseInventoryHistoryTransaction txn : page.getContent()) {
        for (Rule rule : rules) {
          futures.add(buildRuleProcessingTask(rule, job, txn));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v -> futures.stream().map(CompletableFuture::join).toList()).join();
        if (txnProcessedCount.incrementAndGet() % 20 == 0) {
          log.debug("Analyzed {} of {} transactions.", txnProcessedCount.get(), page.getTotalElements());
        }
      }

      pageRequest = PageRequest.of(pageRequest.getPageNumber() + 1, pageRequest.getPageSize());
      page = this.txnRepository.findByInventoryIdentifierOrderByTransactionDate(
          inventoryIdentifier, pageRequest);
    } while (page.hasContent());

    log.info("Completed analysis: {} of {} transactions.", txnProcessedCount.get(), page.getTotalElements());
    this.analysisJobService.completeAnalysis(inventoryIdentifier);
  }

  private CompletableFuture<Boolean> buildRuleProcessingTask(
      Rule rule, AnalysisJob job, WarehouseInventoryHistoryTransaction txn) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        if (rule.validateForRule(txn)) {
          return rule.onScan(job, txn);
        }
      } catch (Exception e) {
        log.error("Failed to analyze! TxnID {}, InventoryIdentifier {}, Rule {} ",
            txn.getTransactionPrimaryKey(), txn.getInventoryIdentifier(), rule.ruleID(), e);
      }
      return true;
    }, threadPoolExecutorService);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
