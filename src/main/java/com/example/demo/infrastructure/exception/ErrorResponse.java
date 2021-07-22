package com.example.demo.infrastructure.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@Getter
@Builder
public class ErrorResponse {

  private final String name;
  private final String message;

  public static Mono<ResponseEntity<ErrorResponse>> entity(ServiceMessage serviceMessage) {
    return Mono.just(ResponseEntity.status(serviceMessage.getStatus())
        .body(ErrorResponse.builder()
            .name(serviceMessage.name())
            .message(serviceMessage.getMessage())
            .build()));
  }

  public static Mono<ResponseEntity<ErrorResponse>> entity(DomainMessage domainMessage) {
    return Mono.just(ResponseEntity.status(domainMessage.getStatus())
        .body(ErrorResponse.builder()
            .name(domainMessage.name())
            .message(domainMessage.getMessage())
            .build()));
  }
}
