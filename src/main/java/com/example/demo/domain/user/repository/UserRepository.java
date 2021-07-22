package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.document.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String>,
    ReactiveQuerydslPredicateExecutor<User> {

  Mono<User> findByEmail(String email);
}
