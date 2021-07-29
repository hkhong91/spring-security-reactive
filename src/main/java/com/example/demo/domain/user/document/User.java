package com.example.demo.domain.user.document;

import com.example.demo.domain.user.value.Authority;
import com.example.demo.domain.user.value.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "User")
@Getter
@Setter
@Builder
public class User {

  @Id
  private final String id;

  @CreatedDate
  private final LocalDateTime createdAt;

  @LastModifiedDate
  private final LocalDateTime updatedAt;

  @Indexed(unique = true)
  private String email;

  @Indexed(unique = true)
  private String name;

  private String password;

  private Gender gender;

  private Set<Authority> authorities;
}
