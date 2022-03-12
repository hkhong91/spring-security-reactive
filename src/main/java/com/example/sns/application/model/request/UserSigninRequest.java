package com.example.sns.application.model.request;

import com.example.sns.application.validation.Password;
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
