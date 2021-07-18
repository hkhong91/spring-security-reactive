package com.example.demo.application.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

  @ExceptionHandler(CustomException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(CustomException exception) {
    return ErrorResponse.entity(exception.getCustomMessage());
  }

  @ExceptionHandler(ServerWebInputException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(ServerWebInputException exception) {
    CustomMessage customMessage = CustomMessage.BAD_REQUEST;
    return Mono.just(ResponseEntity.status(exception.getStatus())
        .body(ErrorResponse.builder()
            .name(customMessage.name())
            .message(exception.getReason())
            .build()));
  }

  @ExceptionHandler(JWTVerificationException.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(JWTVerificationException exception) {
    CustomMessage customMessage = CustomMessage.AUTHORIZATION_ERROR;
    return Mono.just(ResponseEntity.status(customMessage.getStatus())
        .body(ErrorResponse.builder()
            .name(customMessage.name())
            .message(exception.getMessage())
            .build()));
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<ErrorResponse>> handler(Exception exception) {
    log.error(exception.getMessage(), exception);
    CustomMessage customMessage = CustomMessage.SERVER_ERROR;
    return ErrorResponse.entity(customMessage);
  }
}
