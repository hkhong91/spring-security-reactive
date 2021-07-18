package com.example.demo.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomMessage {

  SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
  AUTHORIZATION_ERROR(HttpStatus.UNAUTHORIZED, "Sign in failed"),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
  NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "Not found book."),
  ;

  private final HttpStatus status;
  private final String message;
}
