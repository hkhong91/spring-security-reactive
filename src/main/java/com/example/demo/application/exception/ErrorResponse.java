package com.example.demo.application.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@Getter
@Builder
public class ErrorResponse {

  private final String name;
  private final String message;

  public static Mono<ResponseEntity<ErrorResponse>> entity(CustomMessage customMessage) {
    return Mono.just(ResponseEntity.status(customMessage.getStatus())
        .body(ErrorResponse.builder()
            .name(customMessage.name())
            .message(customMessage.getMessage())
            .build()));
  }
}
