package com.example.demo.application.book.model;

import com.example.demo.domain.book.document.BookReview;
import lombok.Getter;

@Getter
public class BookReviewRequest {

  private String content;

  public BookReview toReview(String bookId) {
    return BookReview.builder()
        .bookId(bookId)
        .content(this.content)
        .build();
  }
}
