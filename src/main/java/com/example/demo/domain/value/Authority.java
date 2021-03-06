package com.example.demo.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {

  USER(Has.USER),
  ADMIN(Has.ADMIN),
  ;

  private final String has;

  public static class Has {
    public static final String USER = "hasAuthority('USER')";
    public static final String ADMIN = "hasAuthority('ADMIN')";
  }
}
