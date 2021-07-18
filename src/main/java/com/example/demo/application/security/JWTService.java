package com.example.demo.application.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.document.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTService {

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.expires}")
  private long expires;

  public String sign(User user) {
    Date now = new Date();
    return JWT.create()
        .withIssuedAt(now)
        .withExpiresAt(Date.from(now.toInstant().plus(Duration.ofHours(this.expires))))
        .withClaim(JWTClaimKey.ID, user.getId())
        .withClaim(JWTClaimKey.NAME, user.getName())
        .sign(this.getAlgorithm());
  }

  public SigninUser verify(String authorization) {
    String token = authorization.substring("Bearer ".length());
    Map<String, Claim> claims = JWT.require(this.getAlgorithm())
        .build()
        .verify(this.decode(token))
        .getClaims();
    return SigninUser.of(claims);
  }

  private DecodedJWT decode(String token) {
    return JWT.decode(token);
  }

  private Algorithm getAlgorithm() {
    return Algorithm.HMAC512(this.secretKey);
  }
}
