package com.example.demo.application.user.controller;

import com.example.demo.application.user.model.request.UserAuthorityRequest;
import com.example.demo.application.user.model.request.UserSigninRequest;
import com.example.demo.application.user.model.request.UserSignupRequest;
import com.example.demo.application.user.model.response.UserResponse;
import com.example.demo.application.user.model.response.UserSigninResponse;
import com.example.demo.application.user.service.UserService;
import com.example.demo.application.user.validation.UserName;
import com.example.demo.infrastructure.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@Validated
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/users/signin")
  public Mono<UserSigninResponse> signin(@RequestBody @Valid UserSigninRequest request) {
    return userService.signin(request);
  }

  @PostMapping("/users/signup")
  public Mono<UserResponse> signup(@RequestBody @Valid UserSignupRequest request) {
    return userService.signup(request);
  }

  @GetMapping("/users/email-check")
  public Mono<Void> checkForEmail(@RequestParam @Email String email) {
    return userService.checkForEmail(email);
  }

  @GetMapping("/users/name-check")
  public Mono<Void> checkForName(@RequestParam @UserName String name) {
    return userService.checkForName(name);
  }

  @PostMapping("/users/me/authorities")
  public Mono<UserResponse> addAuthority(@RequestBody @Valid UserAuthorityRequest request,
                                         @AuthenticationPrincipal AuthUser authUser) {
    return userService.addAuthority(request, authUser);
  }
}
