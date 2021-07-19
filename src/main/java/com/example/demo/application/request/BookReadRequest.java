package com.example.demo.application.request;

import com.example.demo.domain.document.BookRead;
import com.example.demo.domain.value.LikeOrHate;
import lombok.Getter;

@Getter
public class BookReadRequest {

  private LikeOrHate likeOrHate;

  public BookRead toRead(String bookId, String userId) {
    return BookRead.of(bookId, userId, this.likeOrHate);
  }
}
