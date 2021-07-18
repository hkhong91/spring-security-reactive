package com.example.demo.application.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomException extends RuntimeException {

  private final CustomMessage customMessage;

  public static CustomException of(CustomMessage customMessage) {
    return CustomException.builder()
        .customMessage(customMessage)
        .build();
  }
}
