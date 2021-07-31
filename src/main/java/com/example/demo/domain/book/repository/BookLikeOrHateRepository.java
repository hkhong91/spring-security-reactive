package com.example.demo.domain.book.repository;

import com.example.demo.domain.book.document.BookLikeOrHate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface BookLikeOrHateRepository extends ReactiveMongoRepository<BookLikeOrHate, String>,
    ReactiveQuerydslPredicateExecutor<BookLikeOrHate> {

  Mono<BookLikeOrHate> findByBookIdAndUserId(String bookId, String userId);

  Flux<BookLikeOrHate> findAllByBookIdInAndUserId(Set<String> bookIds, String userId);
}
