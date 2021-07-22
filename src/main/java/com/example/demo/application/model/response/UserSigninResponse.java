package com.example.demo.application.model.response;

import com.example.demo.application.security.JwtModel;
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
