package com.example.demo.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DomainMessage {

  SAME_VALUE(HttpStatus.CONFLICT, "Same value."),
  ;

  private final HttpStatus status;
  private final String message;
}
