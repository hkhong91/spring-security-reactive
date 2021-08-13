package com.example.demo.application.book.model.request;

import com.example.demo.domain.book.document.BookReview;
import com.example.demo.infrastructure.security.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewRequest {

  private String content;

  public BookReview toReview(String bookId, AuthUser authUser) {
    return BookReview.builder()
        .bookId(bookId)
        .content(this.content)
        .reviewer(authUser.toCreator())
        .build();
  }
}
