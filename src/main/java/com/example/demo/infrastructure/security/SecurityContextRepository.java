package com.example.demo.infrastructure.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityContextRepository implements ServerSecurityContextRepository {

  private final JwtProvider jwtProvider;
  private final AuthenticationManager authManager;

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return Mono.empty();
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    String token = jwtProvider.getToken(exchange.getRequest()
        .getHeaders()
        .getFirst(HttpHeaders.AUTHORIZATION));
    log.debug("Load security context: {}", token);
    return authManager.authenticate(new UsernamePasswordAuthenticationToken(token, token))
        .map(SecurityContextImpl::new);
  }
}
