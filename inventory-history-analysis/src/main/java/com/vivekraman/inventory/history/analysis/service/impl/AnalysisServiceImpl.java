package com.vivekraman.inventory.history.analysis.service.impl;

import com.vivekraman.inventory.history.analysis.config.InventoryHistoryAnalysisProperties;
import com.vivekraman.inventory.history.analysis.config.MDCPropogatedTask;
import com.vivekraman.inventory.history.analysis.engine.Rule;
import com.vivekraman.inventory.history.analysis.entity.WarehouseInventoryHistoryTransaction;
import com.vivekraman.inventory.history.analysis.repository.WarehouseInventoryHistoryTransactionRepository;
import com.vivekraman.inventory.history.analysis.service.api.AnalysisJobService;
import com.vivekraman.inventory.history.analysis.service.api.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

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
  public void analyzeAsync(String inventoryIdentifier) {
    this.executorService.execute(new MDCPropogatedTask() {
      @Override
      public void execute() {
        analyze(inventoryIdentifier);
      }
    });
  }

  public void analyze(String inventoryIdentifier) {
    Pageable pageRequest = PageRequest.of(0, props.getAnalysisBatchSize());

    Page<WarehouseInventoryHistoryTransaction> page =
        this.txnRepository.findByInventoryIdentifierOrderByTransactionDate(
            inventoryIdentifier, pageRequest);

    do {
      List<CompletableFuture<Boolean>> futures =  new ArrayList<>(page.getSize());

      Map<String, String> mdcContext = MDC.getCopyOfContextMap();
      for (WarehouseInventoryHistoryTransaction txn : page.getContent()) {
        for (Rule rule : rules) {
          CompletableFuture<Boolean> completableFuture =
              CompletableFuture.supplyAsync(() -> {
                MDC.setContextMap(mdcContext);
                return rule.onScan(txn);
              }, threadPoolExecutorService);
          futures.add(completableFuture);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v -> futures.stream().map(CompletableFuture::join).toList()).join();
      }

      pageRequest = page.nextPageable();
      page = this.txnRepository.findByInventoryIdentifierOrderByTransactionDate(
          inventoryIdentifier, pageRequest);
    } while (page.hasContent());

    this.analysisJobService.completeAnalysis(inventoryIdentifier);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
