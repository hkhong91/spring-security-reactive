package com.example.sns.domain.repository;

import com.example.sns.domain.document.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

  Mono<User> findByEmail(String email);

  Mono<Long> countByEmail(String email);

  Mono<Long> countByName(String name);
}
