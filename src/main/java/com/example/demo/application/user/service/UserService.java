package com.example.demo.application.user.service;

import com.example.demo.application.user.model.UserResponse;
import com.example.demo.application.user.model.UserSigninRequest;
import com.example.demo.application.user.model.UserSigninResponse;
import com.example.demo.application.user.model.UserSignupRequest;
import com.example.demo.application.user.security.JwtProvider;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.infrastructure.exception.ServiceException;
import com.example.demo.infrastructure.exception.ServiceMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;

  public Mono<UserSigninResponse> signin(UserSigninRequest request) {
    return userRepository.findByEmail(request.getEmail())
        .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.WRONG_USER)))
        .flatMap(user -> {
          if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return Mono.error(new ServiceException(ServiceMessage.WRONG_USER));
          }
          return Mono.just(jwtProvider.generate(user));
        })
        .map(UserSigninResponse::of);
  }

  public Mono<UserResponse> signup(UserSignupRequest request) {
    return userRepository.save(request.toUser(passwordEncoder))
        .map(UserResponse::of);
  }

  public Mono<Void> checkForEmail(String email) {
    return userRepository.findByEmail(email)
        .flatMap(user -> Objects.isNull(user) ? Mono.empty()
            : Mono.error(new ServiceException(ServiceMessage.EXISTS_USER)));
  }

  public Mono<Void> checkForName(String name) {
    return userRepository.findByName(name)
        .flatMap(user -> Objects.isNull(user) ? Mono.empty()
            : Mono.error(new ServiceException(ServiceMessage.EXISTS_USER)));
  }
}
