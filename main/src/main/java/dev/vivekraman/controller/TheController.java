package dev.vivekraman.controller;

import dev.vivekraman.constants.ApiPath;
import dev.vivekraman.model.Response;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
public class TheController implements ApiPath {
  @GetMapping(SLASH)
  @Hidden
  public void redirectToSwaggerUI(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.sendRedirect(String.format("http://%s/swagger-ui.html",
        StringUtils.defaultString(request.getHeader("host"), "localhost")));
  }
  @RequestMapping(value = "/_ah/warmup",  method = {GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS,
      TRACE,})
  @Hidden
  public Response<Boolean> warmup(HttpServletRequest request) {
    log.info("We're up on https://{}/swagger-ui.html!",
        StringUtils.defaultString(request.getHeader("host"), "localhost"));
    return Response.of(true);
  }
}
