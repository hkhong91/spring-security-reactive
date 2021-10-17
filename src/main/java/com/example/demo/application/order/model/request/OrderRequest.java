package com.example.demo.application.order.model.request;

import com.example.demo.domain.order.document.Order;
import com.example.demo.domain.order.document.sub.OrderedBook;
import com.example.demo.infrastructure.security.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

  @Size(min = 1)
  private List<OrderedBook> orderedBooks;

  public Order toOrder(AuthUser authUser) {
    return Order.builder()
        .userId(authUser.getId())
        .orderedBooks(this.orderedBooks)
        .build();
  }
}
