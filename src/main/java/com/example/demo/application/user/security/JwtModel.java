package com.example.demo.application.user.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtModel {

  private final String accessToken;
}
