package com.example.demo.domain.book.document.sub;

import com.example.demo.application.user.security.AuthUser;
import com.example.demo.exception.DomainException;
import com.example.demo.exception.DomainMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
@Builder
public class Creator {

  @Indexed
  private final String id;

  private String name;

  public static Creator of(AuthUser authUser) {
    return Creator.builder()
        .id(authUser.getId())
        .name(authUser.getName())
        .build();
  }

  public boolean matches(String userId) {
    return this.id.equals(userId);
  }

  public boolean unmatches(String userId) {
    return !this.matches(userId);
  }

  public void verify(String userId) {
    if (this.unmatches(userId)) {
      throw new DomainException(DomainMessage.FORBIDDEN);
    }
  }
}
