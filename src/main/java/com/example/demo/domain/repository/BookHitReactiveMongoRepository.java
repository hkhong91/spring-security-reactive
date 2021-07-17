package com.example.demo.domain.repository;

import com.example.demo.domain.document.BookHit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Mono;

public interface BookHitReactiveMongoRepository extends ReactiveMongoRepository<BookHit, String>,
    ReactiveQuerydslPredicateExecutor<BookHit> {

  Mono<BookHit> findByBookIdAndUserId(String bookId, String userId);
}
