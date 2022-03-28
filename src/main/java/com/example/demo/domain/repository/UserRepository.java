package com.example.demo.domain.repository;

import com.example.demo.domain.document.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

  Mono<User> findByEmail(String email);

  Mono<Long> countByEmail(String email);

  Mono<Long> countByName(String name);
}
