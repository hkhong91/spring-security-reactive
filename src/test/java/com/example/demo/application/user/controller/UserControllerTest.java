package com.example.demo.application.user.controller;

import com.example.demo.application.AbstractControllerTest;
import com.example.demo.application.user.model.request.UserAuthorityRequest;
import com.example.demo.application.user.model.request.UserSigninRequest;
import com.example.demo.application.user.model.request.UserSignupRequest;
import com.example.demo.domain.user.value.Authority;
import com.example.demo.domain.user.value.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest extends AbstractControllerTest {

  @Test
  void signin() {
    client.post()
        .uri("/users/signin")
        .bodyValue(new UserSigninRequest(
            "cjdeks@hanmail.net",
            "abc1234!"))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void signup() {
    client.post()
        .uri("/users/signup")
        .bodyValue(new UserSignupRequest(
            "ghdeks@hanmail.net",
            "홍단",
            "abc1234!",
            Gender.W))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void checkForEmail() {
    client.get()
        .uri(uriBuilder -> uriBuilder.path("/users/email-check")
            .queryParam("email", "abcde@gmail.com")
            .build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void checkForName() {
    client.get()
        .uri(uriBuilder -> uriBuilder.path("/users/name-check")
            .queryParam("name", "에이비씨디이")
            .build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void addAuthority() {
    client.post()
        .uri("/users/me/authorities")
        .header("Authorization", JWT)
        .bodyValue(new UserAuthorityRequest(Authority.ADMIN))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }
}