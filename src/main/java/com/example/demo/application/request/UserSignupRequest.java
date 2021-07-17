package com.example.demo.application.request;

import com.example.demo.domain.document.User;
import com.example.demo.domain.value.Gender;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class UserSignupRequest {

  private String email;

  private String name;

  private String password;

  private Gender gender;

  public User toUser(PasswordEncoder passwordEncoder) {
    return User.builder()
        .email(this.email)
        .name(this.name)
        .password(passwordEncoder.encode(this.password))
        .gender(this.gender)
        .build();
  }
}
