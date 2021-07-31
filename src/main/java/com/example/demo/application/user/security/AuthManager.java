package com.example.demo.application.user.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.infrastructure.exception.ServiceException;
import com.example.demo.infrastructure.exception.ServiceMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthManager implements ReactiveAuthenticationManager {

  private final JwtProvider jwtProvider;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    Object credentials = authentication.getCredentials();
    log.debug("Authenticate credentials: {}", credentials);
    if (Objects.isNull(credentials)) {
      return Mono.error(new ServiceException(ServiceMessage.UNAUTHORIZED));
    }
    try {
      return jwtProvider.authenticate(credentials.toString());
    } catch (JWTVerificationException exception) {
      log.debug(exception.getMessage());
      return Mono.error(new ServiceException(ServiceMessage.UNAUTHORIZED));
    }
  }
}
