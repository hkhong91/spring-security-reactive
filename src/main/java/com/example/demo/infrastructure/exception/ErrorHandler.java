package com.example.demo.infrastructure.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.validation.ValidationException;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(ServiceException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(ServiceException exception) {
    return Mono.just(ErrorResponse.entity(exception));
  }

  @ExceptionHandler(DomainException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(DomainException exception) {
    return Mono.just(ErrorResponse.entity(exception));
  }

  @ExceptionHandler(ServerWebInputException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(ServerWebInputException exception) {
    return Mono.just(ErrorResponse.entity(exception));
  }

  @ExceptionHandler(ValidationException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(ValidationException exception) {
    return Mono.just(ErrorResponse.entity(exception));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(AccessDeniedException exception) {
    return Mono.just(ErrorResponse.entity(exception));
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(Exception exception) {
    return Mono.just(ErrorResponse.entity(exception));
  }
}
