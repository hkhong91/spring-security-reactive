package com.example.demo.infrastructure.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Authorization {

  private String tokenType;
  private String accessToken;
}
