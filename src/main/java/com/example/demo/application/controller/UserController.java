package com.example.demo.application.controller;

import com.example.demo.application.model.request.UserSigninRequest;
import com.example.demo.application.model.request.UserSignupRequest;
import com.example.demo.application.model.response.UserResponse;
import com.example.demo.application.model.response.UserSigninResponse;
import com.example.demo.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/users/signin")
  public Mono<UserSigninResponse> signin(@RequestBody UserSigninRequest request) {
    return userService.signin(request);
  }

  @PostMapping("/users/signup")
  public Mono<UserResponse> signup(@RequestBody UserSignupRequest request) {
    return userService.signup(request);
  }
}
