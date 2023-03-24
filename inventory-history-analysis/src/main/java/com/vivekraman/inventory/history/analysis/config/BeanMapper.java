package com.vivekraman.inventory.history.analysis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
public class BeanMapper {
  @Bean
  public ObjectMapper objectMapperJKT() {
    return new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa"))
        .setTimeZone(TimeZone.getTimeZone("JKT"));
  }
}
