package com.example.demo.application.user.model;

import com.example.demo.application.user.security.JwtModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSigninResponse {

  private final String token;

  public static UserSigninResponse of(JwtModel jwtModel) {
    return UserSigninResponse.builder()
        .token(jwtModel.getAccessToken())
        .build();
  }
}
