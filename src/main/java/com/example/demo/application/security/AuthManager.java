package com.example.demo.application.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.application.exception.ServiceException;
import com.example.demo.application.exception.ServiceMessage;
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
      return Mono.error(new ServiceException(ServiceMessage.AUTHORIZATION_ERROR));
    }
    try {
      Authentication authenticate = jwtProvider.authenticate(credentials.toString());
      return Mono.just(authenticate);
    } catch (JWTVerificationException exception) {
      log.debug(exception.getMessage());
      return Mono.error(new ServiceException(ServiceMessage.AUTHORIZATION_ERROR));
    }
  }
}
