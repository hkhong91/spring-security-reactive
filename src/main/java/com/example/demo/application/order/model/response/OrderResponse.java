package com.example.demo.application.order.model.response;

import com.example.demo.domain.order.document.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {

  private final String id;

  public static OrderResponse of(Order order) {
    return OrderResponse.builder()
        .id(order.getId())
        .build();
  }
}
