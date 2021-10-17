package com.example.demo.domain.order.service;

import com.example.demo.domain.order.document.Order;
import com.example.demo.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderDomainService {

  private final OrderRepository orderRepository;

  public Mono<Order> createOrder(Order order) {
    return orderRepository.save(order);
  }
}
