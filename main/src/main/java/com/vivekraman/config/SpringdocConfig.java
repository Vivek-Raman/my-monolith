package com.vivekraman.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig implements GlobalOpenApiCustomizer {
  private static final String API_GROUP = "common";
  @Bean
  public GroupedOpenApi commonApiGroup() {
    return GroupedOpenApi.builder()
        .group(API_GROUP)
        .packagesToScan("com.vivekraman.controller")
        .build();
  }

  private static final String ALL_APIS = "all";
  @Bean
  public GroupedOpenApi allApiGroup() {
    return GroupedOpenApi.builder()
        .group(ALL_APIS)
        .packagesToScan("com.vivekraman")
        .build();
  }

  @Override
  public void customise(OpenAPI openApi) {
    Info info = new Info();
    info.setTitle("Vivek Raman's Backend Monolith");
    info.setVersion("v1.0");
    info.setDescription("A collection of APIs that Vivek Raman uses across various projects.\n"
        + "Use the definitions on the top-right to pick a project and locate its APIs.");

    Contact contact = new Contact();
    contact.setName("Vivek Raman");
    contact.setEmail("vr.ac4bf@live.com"); // TODO: replace with a cooler email
    contact.setUrl(""); // TODO: get a domain
    info.setContact(contact);

    License license = new License();
    license.setName("a private license");
    license.setIdentifier("a-private-license");
    info.setLicense(license);

    openApi.setInfo(info);
  }
}
