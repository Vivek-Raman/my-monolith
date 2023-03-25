package com.vivekraman.inventory.history.analysis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivekraman.inventory.history.analysis.constants.ApiPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:" + ApiPath.MODULE_NAME + ".properties")
public class InventoryHistoryAnalysisBeans {
  @Bean
  public ObjectMapper objectMapperJKT() {
    return new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa"))
        .setTimeZone(TimeZone.getTimeZone("JKT"));
  }

  @Bean
  public ExecutorService inventoryHistoryAnalysisThreadPoolExecutorService() {
    return Executors.newFixedThreadPool(10);
  }
}
