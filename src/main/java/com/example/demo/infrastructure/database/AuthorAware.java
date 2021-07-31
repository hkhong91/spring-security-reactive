package com.example.demo.infrastructure.database;

import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.book.document.sub.Creator;
import lombok.NonNull;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

public class AuthorAware implements ReactiveAuditorAware<Creator> {

  @Override
  public @NonNull
  Mono<Creator> getCurrentAuditor() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(AuthUser.class)
        .map(Creator::of);
  }
}
