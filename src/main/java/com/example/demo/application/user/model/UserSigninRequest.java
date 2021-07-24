package com.example.demo.application.user.model;

import com.example.demo.application.user.validation.Password;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class UserSigninRequest {

  @Email
  private String email;

  @Password
  private String password;
}
