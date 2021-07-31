package com.example.demo.domain.book.document.sub;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Aggregation {

  public static final String LIKE_COUNT = "aggregation.likeCount";
  public static final String HATE_COUNT = "aggregation.hateCount";
  public static final String REVIEW_COUNT = "aggregation.reviewCount";

  private int likeCount;

  private int hateCount;

  private int reviewCount;
}
