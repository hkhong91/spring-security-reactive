package com.example.demo.domain.exception;

import com.example.demo.application.exception.ServiceException;
import com.example.demo.application.exception.ServiceMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DomainException extends RuntimeException {

  private final DomainMessage domainMessage;

  public static DomainException of(DomainMessage domainMessage) {
    return DomainException.builder()
        .domainMessage(domainMessage)
        .build();
  }
}
