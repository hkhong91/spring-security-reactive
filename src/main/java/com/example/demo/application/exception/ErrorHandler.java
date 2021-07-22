package com.example.demo.application.exception;

import com.example.demo.domain.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

  @ExceptionHandler(ServiceException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(ServiceException exception) {
    return ErrorResponse.entity(exception.getServiceMessage());
  }

  @ExceptionHandler(DomainException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(DomainException exception) {
    return ErrorResponse.entity(exception.getDomainMessage());
  }

  @ExceptionHandler(ServerWebInputException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(ServerWebInputException exception) {
    ServiceMessage serviceMessage = ServiceMessage.BAD_REQUEST;
    return Mono.just(ResponseEntity.status(exception.getStatus())
        .body(ErrorResponse.builder()
            .name(serviceMessage.name())
            .message(exception.getReason())
            .build()));
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(Exception exception) {
    log.error(exception.getMessage(), exception);
    ServiceMessage serviceMessage = ServiceMessage.SERVER_ERROR;
    return ErrorResponse.entity(serviceMessage);
  }
}
