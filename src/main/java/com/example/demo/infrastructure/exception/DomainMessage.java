package com.example.demo.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public enum DomainMessage {

  SAME_VALUE(HttpStatus.CONFLICT, "Same value."),
  FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden."),
  STRANGE_DATA(HttpStatus.UNPROCESSABLE_ENTITY, "Strange data."),
  NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "Not found book."),
  NOT_FOUND_BOOK_REVIEW(HttpStatus.NOT_FOUND, "Not found book review."),
  NOT_LIKED_OR_HATED_BOOK(HttpStatus.CONFLICT, "Not liked or hated book"),
  NOT_LIKED_BOOK(HttpStatus.CONFLICT, "Not liked book"),
  NOT_HATED_BOOK(HttpStatus.CONFLICT, "Not hated book"),
  ALREADY_LIKED_BOOK(HttpStatus.CONFLICT, "Already liked book"),
  ALREADY_HATED_BOOK(HttpStatus.CONFLICT, "Already hated book"),
  ;

  private final HttpStatus status;
  private final String defaultMessage;

  public DomainException exception() {
    return new DomainException(this);
  }

  public <T> Mono<T> error() {
    return Mono.error(this.exception());
  }
}
