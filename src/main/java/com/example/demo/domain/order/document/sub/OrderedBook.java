package com.example.demo.domain.order.document.sub;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderedBook {
  private String bookId;

  private String sellerId;

  private Integer price;
}
