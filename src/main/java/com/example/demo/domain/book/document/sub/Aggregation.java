package com.example.demo.domain.book.document.sub;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Aggregation {

  private int likeCount;

  private int hateCount;

  private int reviewCount;
}
