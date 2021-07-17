package com.example.demo.application.security;

import com.auth0.jwt.interfaces.Claim;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthWebFilter implements WebFilter {

  private final JWTService jwtService;
  private final SigninUser signinUser;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    HttpHeaders headers = request.getHeaders();
    String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isNotEmpty(authorization)) {
      String token = authorization.substring("Bearer ".length());
      Map<String, Claim> claims = jwtService.verify(token);
      signinUser.set(claims);
    }
    return chain.filter(exchange);
  }
}
