package com.example.demo.application.security;

import com.auth0.jwt.interfaces.Claim;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
public class SigninUser {

  private String id;
  private String name;

  public void set(Map<String, Claim> claims) {
    this.id = claims.get(ClaimConstant.ID).asString();
    this.name = claims.get(ClaimConstant.NAME).asString();
  }
}
