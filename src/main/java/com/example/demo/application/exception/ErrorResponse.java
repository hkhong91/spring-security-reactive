package com.example.demo.application.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

  private final String name;
  private final String message;
}
