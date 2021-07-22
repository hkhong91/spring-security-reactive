package com.example.demo.domain.book.repository;

import com.example.demo.domain.book.document.BookReview;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Flux;

public interface BookReviewRepository extends ReactiveMongoRepository<BookReview, String>,
    ReactiveQuerydslPredicateExecutor<BookReview> {

  Flux<BookReview> findAllByBookId(String bookId);
}
