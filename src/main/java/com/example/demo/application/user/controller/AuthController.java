package com.example.demo.application.user.controller;

import com.example.demo.application.user.model.UserResponse;
import com.example.demo.application.user.model.UserSigninRequest;
import com.example.demo.application.user.model.UserSigninResponse;
import com.example.demo.application.user.model.UserSignupRequest;
import com.example.demo.application.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/auth/signin")
  public Mono<UserSigninResponse> signin(@RequestBody @Valid UserSigninRequest request) {
    return authService.signin(request);
  }

  @PostMapping("/auth/signup")
  public Mono<UserResponse> signup(@RequestBody @Valid UserSignupRequest request) {
    return authService.signup(request);
  }
}
