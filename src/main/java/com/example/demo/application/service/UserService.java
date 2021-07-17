package com.example.demo.application.service;

import com.example.demo.application.request.UserSigninRequest;
import com.example.demo.application.request.UserSignupRequest;
import com.example.demo.application.response.UserResponse;
import com.example.demo.application.response.UserSigninResponse;
import com.example.demo.application.security.JWTService;
import com.example.demo.domain.repository.UserReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserReactiveMongoRepository userReactiveMongoRepository;
  private final JWTService jwtService;
  private final PasswordEncoder passwordEncoder;

  public Mono<UserSigninResponse> signin(UserSigninRequest request) {
    return userReactiveMongoRepository.findByEmail(request.getEmail())
        .flatMap(user -> Mono.just(jwtService.sign(user)))
        .map(UserSigninResponse::of);
  }

  public Mono<UserResponse> signup(UserSignupRequest request) {
    return userReactiveMongoRepository.save(request.toUser(passwordEncoder))
        .map(UserResponse::of);
  }
}
