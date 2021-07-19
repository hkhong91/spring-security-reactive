package com.example.demo.application.response;

import com.example.demo.domain.document.BookRead;
import com.example.demo.domain.value.LikeOrHate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookReadResponse {

  private final String id;
  private final String bookId;
  private final String userId;
  private final LikeOrHate likeOrHate;

  public static BookReadResponse of(BookRead read) {
    return BookReadResponse.builder()
        .id(read.getId())
        .bookId(read.getBookId())
        .userId(read.getUserId())
        .likeOrHate(read.getLikeOrHate())
        .build();
  }
}
