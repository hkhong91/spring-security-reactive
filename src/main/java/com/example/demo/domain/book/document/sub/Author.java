package com.example.demo.domain.book.document.sub;

import com.example.demo.application.user.security.AuthUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
public class Author {

  private final String id;
  private String name;

  public static Author of(AuthUser authUser) {
    return Author.builder()
        .id(authUser.getId())
        .name(authUser.getName())
        .build();
  }
}
