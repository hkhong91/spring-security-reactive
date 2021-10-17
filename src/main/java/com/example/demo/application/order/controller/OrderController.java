package com.example.demo.application.order.controller;

import com.example.demo.application.order.model.request.OrderRequest;
import com.example.demo.application.order.model.response.OrderResponse;
import com.example.demo.application.order.service.OrderService;
import com.example.demo.infrastructure.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/orders")
  public Mono<OrderResponse> order(@RequestBody @Valid OrderRequest request,
                                   @AuthenticationPrincipal AuthUser authUser) {
    return orderService.order(request, authUser);
  }
}
