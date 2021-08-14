package com.example.demo.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public enum ServiceMessage {

  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized."),
  WRONG_USER(HttpStatus.UNAUTHORIZED, "Wrong user."),
  EXISTS_USER(HttpStatus.CONFLICT, "Exists user."),
  ;

  private final HttpStatus status;
  private final String defaultMessage;

  public ServiceException exception() {
    return new ServiceException(this);
  }

  public <T> Mono<T> error() {
    return Mono.error(this.exception());
  }
}
