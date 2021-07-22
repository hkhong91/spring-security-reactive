package com.example.demo.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DomainException extends RuntimeException {

  private final DomainMessage domainMessage;
}
