package com.example.demo.application.service;

import com.example.demo.application.request.UserSigninRequest;
import com.example.demo.application.request.UserSignupRequest;
import com.example.demo.application.response.UserResponse;
import com.example.demo.application.response.UserSigninResponse;
import com.example.demo.application.security.JWTService;
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
  private final JWTService jwtService;
  private final PasswordEncoder passwordEncoder;

  public Mono<UserSigninResponse> signin(UserSigninRequest request) {
    return userRepository.findByEmail(request.getEmail())
        .flatMap(user -> Mono.just(jwtService.sign(user)))
        .map(UserSigninResponse::of);
  }

  public Mono<UserResponse> signup(UserSignupRequest request) {
    return userRepository.save(request.toUser(passwordEncoder))
        .map(UserResponse::of);
  }
}
