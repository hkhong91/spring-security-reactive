package com.example.demo.application.book.model;

import com.example.demo.domain.book.document.BookComment;
import com.example.demo.domain.book.document.sub.Author;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookCommentResponse {

  private final String id;
  private final String bookId;
  private final Author author;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final String content;

  public static BookCommentResponse of(BookComment comment) {
    return BookCommentResponse.builder()
        .id(comment.getId())
        .bookId(comment.getBookId())
        .author(comment.getAuthor())
        .createdAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt())
        .content(comment.getContent())
        .build();
  }
}
