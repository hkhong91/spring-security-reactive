package com.example.sns.application.model.response;

import com.example.sns.domain.document.User;
import com.example.sns.domain.value.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

  private String id;
  private String name;
  private Gender gender;
  private LocalDateTime createdAt;

  public static UserResponse of(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .gender(user.getGender())
        .createdAt(user.getCreatedAt())
        .build();
  }
}
