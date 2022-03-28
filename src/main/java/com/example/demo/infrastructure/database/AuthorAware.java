package com.example.demo.infrastructure.database;

import com.example.demo.infrastructure.security.SNSUserDetails;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

public class AuthorAware implements ReactiveAuditorAware<String> {

  @Override
  public Mono<String> getCurrentAuditor() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SNSUserDetails.class)
        .map(SNSUserDetails::getId);
  }
}
