package com.vivekraman.config;

import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig implements ApplicationContextAware {
  private ApplicationContext applicationContext;
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
  @Bean
  public SpringDocConfigProperties springDocConfigProperties() {
    SpringDocConfigProperties props = new SpringDocConfigProperties();
    new SpringDocConfigProperties.ApiDocs();
//    props.setApiDocs();

    for (String beanName : applicationContext.getBeanNamesForType(ApiGroup.class)) {
      Object bean = applicationContext.getBean(beanName);
      if (bean instanceof ApiGroup apiGroup) {
        props.addGroupConfig(apiGroup.apiGroup());
      }
    }

    return props;
  }

  public static interface ApiGroup {
    SpringDocConfigProperties.GroupConfig apiGroup();
  }
}
