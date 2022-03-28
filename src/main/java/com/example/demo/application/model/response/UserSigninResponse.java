package com.example.demo.application.model.response;

import com.example.demo.infrastructure.security.Authorization;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSigninResponse {

  private String tokenType;
  private String accessToken;

  public static UserSigninResponse of(Authorization jwtModel) {
    return UserSigninResponse.builder()
        .tokenType(jwtModel.getTokenType())
        .accessToken(jwtModel.getAccessToken())
        .build();
  }
}
