package com.example.demo.application.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServiceException extends RuntimeException {

  private final ServiceMessage serviceMessage;

  public static ServiceException of(ServiceMessage serviceMessage) {
    return ServiceException.builder()
        .serviceMessage(serviceMessage)
        .build();
  }
}
