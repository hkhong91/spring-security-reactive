package com.example.demo.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServiceMessage {

  SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized."),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
  NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "Not found book."),
  NOT_FOUND_BOOK_REVIEW(HttpStatus.NOT_FOUND, "Not found book review."),
  WRONG_USER(HttpStatus.UNAUTHORIZED, "Wrong user."),
  ;

  private final HttpStatus status;
  private final String message;
}
