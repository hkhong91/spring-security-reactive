package com.example.demo.domain.repository;

import com.example.demo.domain.document.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Mono;

public interface UserReactiveMongoRepository extends ReactiveMongoRepository<User, String>,
    ReactiveQuerydslPredicateExecutor<User> {

  Mono<User> findByEmail(String email);
}
