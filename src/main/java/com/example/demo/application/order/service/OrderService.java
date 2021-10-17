package com.example.demo.application.order.service;

import com.example.demo.application.order.model.request.OrderRequest;
import com.example.demo.application.order.model.response.OrderResponse;
import com.example.demo.domain.order.service.OrderDomainService;
import com.example.demo.infrastructure.security.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

  private final OrderDomainService orderDomainService;

  public Mono<OrderResponse> order(OrderRequest request,
                                   AuthUser authUser) {
    return orderDomainService.createOrder(request.toOrder(authUser))
        .map(OrderResponse::of);
  }
}
