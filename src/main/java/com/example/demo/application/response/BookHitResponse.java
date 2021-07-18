package com.example.demo.application.response;

import com.example.demo.domain.document.BookHit;
import com.example.demo.domain.value.LikeOrHate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookHitResponse {

  private final String id;
  private final String bookId;
  private final String userId;
  private final LikeOrHate likeOrHate;

  public static BookHitResponse of(BookHit hit) {
    return BookHitResponse.builder()
        .id(hit.getId())
        .bookId(hit.getBookId())
        .userId(hit.getUserId())
        .likeOrHate(hit.getLikeOrHate())
        .build();
  }
}
