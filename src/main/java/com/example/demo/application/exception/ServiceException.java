package com.example.demo.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServiceException extends RuntimeException {

  private final ServiceMessage serviceMessage;
}
