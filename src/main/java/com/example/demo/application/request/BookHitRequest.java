package com.example.demo.application.request;

import com.example.demo.domain.document.BookHit;
import com.example.demo.domain.value.LikeOrHate;
import lombok.Getter;

@Getter
public class BookHitRequest {

  private LikeOrHate likeOrHate;

  public BookHit toBookHit(String bookId, String userId) {
    return BookHit.builder()
        .bookId(bookId)
        .userId(userId)
        .likeOrHate(this.likeOrHate)
        .build();
  }
}
