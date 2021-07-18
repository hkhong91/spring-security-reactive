package com.example.demo.application.security;

import com.auth0.jwt.interfaces.Claim;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class SigninUser {

  private final String id;
  private final String name;

  public static SigninUser of(Map<String, Claim> claims) {
    return SigninUser.builder()
        .id(claims.get(JWTClaimKey.ID).asString())
        .name(claims.get(JWTClaimKey.NAME).asString())
        .build();
  }
}
