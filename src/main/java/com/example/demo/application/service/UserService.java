package com.example.demo.application.service;

import com.example.demo.application.exception.ServiceException;
import com.example.demo.application.exception.ServiceMessage;
import com.example.demo.application.model.request.UserSigninRequest;
import com.example.demo.application.model.request.UserSignupRequest;
import com.example.demo.application.model.response.UserResponse;
import com.example.demo.application.model.response.UserSigninResponse;
import com.example.demo.application.security.JwtProvider;
import com.example.demo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
}
