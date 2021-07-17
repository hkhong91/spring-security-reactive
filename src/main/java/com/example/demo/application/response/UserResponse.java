package com.example.demo.application.response;

import com.example.demo.domain.document.User;
import com.example.demo.domain.value.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

  private final String id;
  private final String name;
  private final Gender gender;

  public static UserResponse of(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .gender(user.getGender())
        .build();
  }
}
