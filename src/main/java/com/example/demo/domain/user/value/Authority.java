package com.example.demo.domain.user.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {

  USER(Has.USER),
  SELLER(Has.SELLER),
  ;

  private final String has;

  public static class Has {
    public static final String USER = "hasAuthority('USER')";
    public static final String SELLER = "hasAuthority('SELLER')";
  }
}
