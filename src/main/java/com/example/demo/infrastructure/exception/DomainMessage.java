package com.example.demo.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DomainMessage {

  SAME_VALUE(HttpStatus.CONFLICT, "Same value."),
  FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden."),
  STRANGE_DATA(HttpStatus.UNPROCESSABLE_ENTITY, "Strange data."),
  ;

  private final HttpStatus status;
  private final String defaultMessage;
}
