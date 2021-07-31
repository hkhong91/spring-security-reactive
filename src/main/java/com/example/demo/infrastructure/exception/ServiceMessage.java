package com.example.demo.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServiceMessage {

  SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access denied."),
  INPUT_ERROR(HttpStatus.BAD_REQUEST, "Input error."),
  VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Fail validation"),
  WRONG_USER(HttpStatus.UNAUTHORIZED, "Wrong user."),
  EXISTS_USER(HttpStatus.CONFLICT, "Exists user."),
  ;

  private final HttpStatus status;
  private final String defaultMessage;
}
