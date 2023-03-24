package com.vivekraman.controller;

import com.vivekraman.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class TheAdvice {
  @ExceptionHandler
  public ResponseEntity<Response<Object>> onException(Exception e) {
    log.error("ERROR caught in TheAdvice", e);
    String error = "ERROR caught in TheAdvice: " + e.getMessage();

    Response<Object> body = Response.error(error);
    return ResponseEntity.internalServerError()
        .contentType(MediaType.APPLICATION_JSON)
        .contentLength(error.length())
        .body(body);
  }
}
