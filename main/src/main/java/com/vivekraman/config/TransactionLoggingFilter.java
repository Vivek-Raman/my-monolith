package com.vivekraman.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Component
@Slf4j
@ConditionalOnProperty("${false}")
public class TransactionLoggingFilter implements WebMvcConfigurer, HandlerInterceptor {
  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.debug("API Received! Request {}", new RequestLog(request).toJSON(objectMapper));
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    log.debug("API Response! Response {}", new ResponseLog(request, response).toJSON(objectMapper));
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(this);
    WebMvcConfigurer.super.addInterceptors(registry);
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RequestLog {
    private String path;
    private String method;
    private Map<String, String[]> params;
    private Map<String, Object> body;

    public RequestLog(HttpServletRequest request) {
      this.path = request.getServletPath();
      this.method = request.getMethod();
      this.params = request.getParameterMap();
    }

    public String toJSON(ObjectMapper objectMapper) throws Exception {
      return objectMapper.writeValueAsString(this);
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ResponseLog extends RequestLog {
    private Map<String, Object> response;

    public ResponseLog(HttpServletRequest request, HttpServletResponse response) {
      super(request);
//      this.response = response.
    }

    public String toJSON(ObjectMapper objectMapper) throws Exception {
      return objectMapper.writeValueAsString(this);
    }
  }
}
