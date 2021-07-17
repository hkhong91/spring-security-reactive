package com.example.demo.application.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.example.demo.domain.document.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
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
        .withClaim(ClaimConstant.ID, user.getId())
        .withClaim(ClaimConstant.NAME, user.getName())
        .sign(this.getAlgorithm());
  }

  public Map<String, Claim> verify(String token) {
    return JWT.require(this.getAlgorithm())
        .build()
        .verify(token)
        .getClaims();
  }

  public Map<String, Claim> decode(String token) {
    return JWT.decode(token).getClaims();
  }

  private Algorithm getAlgorithm() {
    return Algorithm.HMAC512(this.secretKey);
  }
}
