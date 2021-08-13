package com.example.demo.application.user.model.request;

import com.example.demo.application.user.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSigninRequest {

  @Email
  private String email;

  @Password
  private String password;
}
