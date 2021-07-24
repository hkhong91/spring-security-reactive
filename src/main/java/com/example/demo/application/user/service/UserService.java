package com.example.demo.application.user.service;

import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.infrastructure.exception.ServiceException;
import com.example.demo.infrastructure.exception.ServiceMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  public Mono<Void> checkForEmail(String email) {
    return userRepository.findByEmail(email)
        .flatMap(user -> Objects.isNull(user) ? Mono.empty()
            : Mono.error(new ServiceException(ServiceMessage.EXISTS_USER)));
  }

  public Mono<Void> checkForName(String name) {
    return userRepository.findByName(name)
        .flatMap(user -> Objects.isNull(user) ? Mono.empty()
            : Mono.error(new ServiceException(ServiceMessage.EXISTS_USER)));
  }
}
