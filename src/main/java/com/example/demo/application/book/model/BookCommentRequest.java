package com.example.demo.application.book.model;

import com.example.demo.domain.book.document.BookComment;
import lombok.Getter;

@Getter
public class BookCommentRequest {

  private String content;

  public BookComment toComment(String bookId) {
    return BookComment.builder()
        .bookId(bookId)
        .content(this.content)
        .build();
  }
}
