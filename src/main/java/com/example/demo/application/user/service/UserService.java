package com.example.demo.application.user.service;

import com.example.demo.application.user.model.request.UserAuthorityRequest;
import com.example.demo.application.user.model.request.UserSigninRequest;
import com.example.demo.application.user.model.request.UserSignupRequest;
import com.example.demo.application.user.model.response.UserResponse;
import com.example.demo.application.user.model.response.UserSigninResponse;
import com.example.demo.application.user.security.AuthUser;
import com.example.demo.application.user.security.JwtProvider;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.exception.ServiceMessage;
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
        .switchIfEmpty(ServiceMessage.WRONG_USER.error())
        .flatMap(user -> {
          if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ServiceMessage.WRONG_USER.error();
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
    return userRepository.countByEmail(email)
        .flatMap(count -> {
          if (count == 0) return Mono.empty();
          else return ServiceMessage.EXISTS_USER.error();
        });
  }

  public Mono<Void> checkForName(String name) {
    return userRepository.countByName(name)
        .flatMap(count -> {
          if (count == 0) return Mono.empty();
          else return ServiceMessage.EXISTS_USER.error();
        });
  }

  public Mono<UserResponse> addAuthority(UserAuthorityRequest request, AuthUser authUser) {
    return userRepository.findById(authUser.getId())
        .flatMap(user -> {
          user.getAuthorities().add(request.getAuthority());
          return userRepository.save(user);
        })
        .map(UserResponse::of);
  }
}
