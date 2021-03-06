package com.example.demo.application.controller;

import com.example.demo.application.model.request.UserAuthorityRequest;
import com.example.demo.application.model.request.UserSigninRequest;
import com.example.demo.application.model.request.UserSignupRequest;
import com.example.demo.application.model.response.UserResponse;
import com.example.demo.application.model.response.UserSigninResponse;
import com.example.demo.application.service.UserService;
import com.example.demo.application.validation.UserName;
import com.example.demo.infrastructure.security.SNSUserDetails;
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
                                         @AuthenticationPrincipal SNSUserDetails userDetails) {
    return userService.addAuthority(request, userDetails);
  }
}
