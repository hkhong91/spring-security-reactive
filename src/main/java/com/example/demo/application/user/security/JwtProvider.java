package com.example.demo.application.user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.user.document.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.exception.ServiceException;
import com.example.demo.exception.ServiceMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

  private static final String TYPE = "Bearer";
  private static final String ID = "id";
  private final UserRepository userRepository;
  @Value("${jwt.secret-key}")
  private String secretKey;
  @Value("${jwt.expires}")
  private long expires;

  public String getToken(String authorization) {
    if (Objects.isNull(authorization)) {
      log.debug("Empty authorization.");
      return null;
    }
    if (!authorization.startsWith(TYPE)) {
      log.debug("Abnormal authorization. {}", authorization);
      throw new ServiceException(ServiceMessage.UNAUTHORIZED);
    }
    return authorization.substring(TYPE.length() + 1);
  }

  public Mono<Authentication> authenticate(String token) {
    DecodedJWT decodedJWT = this.verify(token);
    Map<String, Claim> claims = decodedJWT.getClaims();
    String userId = claims.get(ID).asString();
    return userRepository.findById(userId)
        .map(user -> {
          AuthUser authUser = AuthUser.of(user);
          return new UsernamePasswordAuthenticationToken(authUser, "", authUser.getAuthorities());
        });
  }

  public JwtModel generate(User user) {
    return JwtModel.builder()
        .tokenType(TYPE)
        .accessToken(this.sign(user))
        .build();
  }

  private String sign(User user) {
    Date now = new Date();
    return JWT.create()
        .withIssuedAt(now)
        .withExpiresAt(Date.from(now.toInstant().plus(Duration.ofHours(this.expires))))
        .withClaim(ID, user.getId())
        .sign(this.getAlgorithm());
  }

  private DecodedJWT verify(String token) {
    log.debug("Verify Token: {}", token);
    return JWT.require(this.getAlgorithm())
        .build()
        .verify(this.decode(token));
  }

  private DecodedJWT decode(String token) {
    return JWT.decode(token);
  }

  private Algorithm getAlgorithm() {
    return Algorithm.HMAC512(this.secretKey);
  }
}
