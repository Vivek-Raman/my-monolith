package dev.vivekraman.inventory.history.analysis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class InventoryHistoryAnalysisBeans {
  @Bean
  public ExecutorService inventoryHistoryAnalysisThreadPoolExecutorService() {
    return Executors.newFixedThreadPool(10);
  }
}
