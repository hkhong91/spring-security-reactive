package com.example.sns.application.model.request;

import com.example.sns.domain.value.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorityRequest {

  private Authority authority;
}
