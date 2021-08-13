package com.example.demo.infrastructure.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtModel {

  private final String tokenType;
  private final String accessToken;
}
