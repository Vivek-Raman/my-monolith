package dev.vivekraman.controller;

import dev.vivekraman.constants.ApiPath;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TheController implements ApiPath {
  @GetMapping(SLASH)
  @Hidden
  public void redirectToSwaggerUI(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.sendRedirect(String.format("http://%s%s/swagger-ui.html",
        StringUtils.defaultString(request.getHeader("host"), "localhost"), CONTEXT_PATH));
  }
}
