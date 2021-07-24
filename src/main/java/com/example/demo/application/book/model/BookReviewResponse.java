package com.example.demo.application.book.model;

import com.example.demo.domain.book.document.BookReview;
import com.example.demo.domain.book.document.sub.Author;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookReviewResponse {

  private final String id;
  private final String bookId;
  private final Author author;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final String content;
  private final boolean deleted;

  public static BookReviewResponse of(BookReview review) {
    boolean deleted = review.isDeleted();
    return BookReviewResponse.builder()
        .id(review.getId())
        .bookId(review.getBookId())
        .author(review.getAuthor())
        .createdAt(review.getCreatedAt())
        .updatedAt(review.getUpdatedAt())
        .content(deleted ? null : review.getContent())
        .deleted(deleted)
        .build();
  }
}
