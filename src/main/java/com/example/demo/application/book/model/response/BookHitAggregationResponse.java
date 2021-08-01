package com.example.demo.application.book.model.response;

import com.example.demo.domain.book.model.BookHitAggregation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookHitAggregationResponse {

  private final String bookId;
  private final long matchedCount;
  private final long modifiedCount;

  public static BookHitAggregationResponse of(BookHitAggregation aggregation) {
    return BookHitAggregationResponse.builder()
        .bookId(aggregation.getBookId())
        .matchedCount(aggregation.getMatchedCount())
        .modifiedCount(aggregation.getModifiedCount())
        .build();
  }
}
