package dev.vivekraman.external.config.controller;

import dev.vivekraman.external.config.constants.ApiPath;
import dev.vivekraman.external.config.entity.ExternalConfigParameter;
import dev.vivekraman.external.config.service.api.ExternalConfigParameterService;
import dev.vivekraman.model.Response;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(ApiPath.BASE_URL)
@RestController
@RequiredArgsConstructor
public class ExternalConfigController {
  private final ExternalConfigParameterService externalConfigParameterService;

  @GetMapping(path = ApiPath.FIND)
  public Response<List<ExternalConfigParameter>> findByConfigKey(
      @RequestParam(required = false) String configKey) {
    if (StringUtils.isBlank(configKey)) {
      return Response.of(externalConfigParameterService.findAll());
    }

    return Response.of(externalConfigParameterService.findByConfigKeyIn(configKey));
  }

  @PostMapping(path = ApiPath.UPSERT)
  public Response<ExternalConfigParameter> upsertConfig(
      @RequestParam String configKey, @RequestParam String configValue) {
    ExternalConfigParameter param = externalConfigParameterService.upsert(configKey, configValue);
    return Response.of(param);
  }
}
