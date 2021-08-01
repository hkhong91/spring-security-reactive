package com.example.demo.domain.book.model;

import com.mongodb.client.result.UpdateResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookHitAggregation {

  private final String bookId;
  private final long matchedCount;
  private final long modifiedCount;

  public static BookHitAggregation of(String bookId) {
    return BookHitAggregation.builder()
        .bookId(bookId)
        .matchedCount(0)
        .modifiedCount(0)
        .build();
  }

  public static BookHitAggregation of(String bookId, UpdateResult result) {
    return BookHitAggregation.builder()
        .bookId(bookId)
        .matchedCount(result.getMatchedCount())
        .modifiedCount(result.getModifiedCount())
        .build();
  }
}
