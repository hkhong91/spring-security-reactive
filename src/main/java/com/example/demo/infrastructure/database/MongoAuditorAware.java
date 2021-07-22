package com.example.demo.infrastructure.database;

import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.book.document.sub.Author;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

@Configuration
public class MongoAuditorAware implements ReactiveAuditorAware<Author> {

  @Override
  public @NonNull Mono<Author> getCurrentAuditor() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(AuthUser.class)
        .map(Author::of);
  }
}
