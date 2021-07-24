package com.example.demo.application.user.controller;

import com.example.demo.application.user.service.UserService;
import com.example.demo.application.user.validation.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Email;

@RestController
@Validated
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users/email-check")
  public Mono<Void> checkForEmail(@RequestParam @Email String email) {
    return userService.checkForEmail(email);
  }

  @GetMapping("/users/name-check")
  public Mono<Void> checkForName(@RequestParam @UserName String name) {
    return userService.checkForName(name);
  }
}
