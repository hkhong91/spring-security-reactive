package com.example.demo.application.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSigninResponse {

  private final String token;

  public static UserSigninResponse of(String token) {
    return UserSigninResponse.builder()
        .token(token)
        .build();
  }
}
