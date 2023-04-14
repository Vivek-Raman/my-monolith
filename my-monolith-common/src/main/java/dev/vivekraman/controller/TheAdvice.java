package dev.vivekraman.controller;

import dev.vivekraman.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

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

  @ExceptionHandler(value = NoSuchElementException.class)
  public ResponseEntity<Response<Object>> onNoSuchElementException(NoSuchElementException e) {
    log.error("NO SUCH ELEMENT caught in TheAdvice", e);
    String error = "NO SUCH ELEMENT caught in TheAdvice: " + e.getMessage();

    Response<Object> body = Response.error(error);
    return ResponseEntity.badRequest()
        .contentType(MediaType.APPLICATION_JSON)
        .contentLength(error.length())
        .body(body);
  }
}
