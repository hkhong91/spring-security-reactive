package com.example.demo.application.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ValidationException;
import java.util.stream.Collectors;

@Getter
@Builder
@Slf4j
public class ErrorResponse {

  private final String name;
  private final String message;

  public static ResponseEntity<ErrorResponse> entity(ServiceException exception) {
    log.debug("ServiceException Message! {}", exception.getServiceMessage());
    ServiceMessage serviceMessage = exception.getServiceMessage();
    return ResponseEntity.status(serviceMessage.getStatus())
        .body(ErrorResponse.builder()
            .name(serviceMessage.name())
            .message(serviceMessage.getDefaultMessage())
            .build());
  }

  public static ResponseEntity<ErrorResponse> entity(ServerWebInputException exception) {
    log.warn("ServerWebInputException! {}", exception.getClass().getSimpleName());
    String errorName = exception.getClass().getSimpleName();
    if (exception instanceof WebExchangeBindException) {
      return ResponseEntity.status(exception.getStatus())
          .body(ErrorResponse.builder()
              .name(errorName)
              .message(((WebExchangeBindException) exception).getBindingResult()
                  .getAllErrors()
                  .stream()
                  .map(ObjectError::getDefaultMessage)
                  .collect(Collectors.joining()))
              .build());
    }
    return ResponseEntity.status(exception.getStatus())
        .body(ErrorResponse.builder()
            .name(errorName)
            .message(exception.getReason())
            .build());
  }

  public static ResponseEntity<ErrorResponse> entity(ValidationException exception) {
    log.warn("ValidationException! {}", exception.getClass().getSimpleName());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.builder()
            .name(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .build());
  }

  public static ResponseEntity<ErrorResponse> entity(AccessDeniedException exception) {
    log.warn("AccessDeniedException! {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.builder()
            .name(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .build());
  }

  public static ResponseEntity<ErrorResponse> entity(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder()
            .name(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .build());
  }
}
