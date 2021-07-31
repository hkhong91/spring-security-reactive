package com.example.demo.application.book.model.response;

import com.example.demo.domain.book.document.BookLikeOrHate;
import com.example.demo.domain.book.value.LikeOrHate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookLikeOrHateResponse {

  private final String id;

  private final String bookId;

  private final LikeOrHate value;

  private final LocalDateTime createdAt;

  public static BookLikeOrHateResponse empty(String bookId) {
    return BookLikeOrHateResponse.builder()
        .bookId(bookId)
        .build();
  }

  public static BookLikeOrHateResponse of(BookLikeOrHate likeOrHate) {
    return BookLikeOrHateResponse.builder()
        .id(likeOrHate.getId())
        .bookId(likeOrHate.getBookId())
        .value(likeOrHate.getValue())
        .createdAt(likeOrHate.getCreatedAt())
        .build();
  }
}
