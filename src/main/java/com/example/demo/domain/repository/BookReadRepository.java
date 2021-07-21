package com.example.demo.domain.repository;

import com.example.demo.domain.document.BookRead;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface BookReadRepository extends ReactiveMongoRepository<BookRead, String>,
    ReactiveQuerydslPredicateExecutor<BookRead> {

  Mono<BookRead> findByBookIdAndUserId(String bookId, String userId);

  Flux<BookRead> findAllByBookIdInAndUserId(Set<String> bookIds, String userId);
}
