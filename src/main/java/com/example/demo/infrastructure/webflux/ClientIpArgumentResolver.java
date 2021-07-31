package com.example.demo.infrastructure.webflux;

import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ClientIpArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(String.class) &&
        parameter.hasParameterAnnotation(ClientIp.class);
  }

  @Override
  public Mono<Object> resolveArgument(MethodParameter parameter,
                                      BindingContext bindingContext,
                                      ServerWebExchange exchange) {
    InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
    if (remoteAddress != null) {
      InetAddress address = remoteAddress.getAddress();
      return Mono.just(address.getHostAddress());
    }
    return Mono.empty();
  }
}
