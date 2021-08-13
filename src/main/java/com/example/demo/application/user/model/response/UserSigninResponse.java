package com.example.demo.application.user.model.response;

import com.example.demo.infrastructure.security.JwtModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSigninResponse {

  private final String tokenType;
  private final String accessToken;

  public static UserSigninResponse of(JwtModel jwtModel) {
    return UserSigninResponse.builder()
        .tokenType(jwtModel.getTokenType())
        .accessToken(jwtModel.getAccessToken())
        .build();
  }
}
