package com.example.demo.application.user.model;

import com.example.demo.application.user.validation.Password;
import com.example.demo.application.user.validation.UserName;
import com.example.demo.domain.user.document.User;
import com.example.demo.domain.user.value.Gender;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;

@Getter
public class UserSignupRequest {

  @Email
  private String email;

  @UserName
  private String name;

  @Password
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
