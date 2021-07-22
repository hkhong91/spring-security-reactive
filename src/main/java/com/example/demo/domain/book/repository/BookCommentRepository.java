package com.example.demo.domain.book.repository;

import com.example.demo.domain.book.document.BookComment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Flux;

public interface BookCommentRepository extends ReactiveMongoRepository<BookComment, String>,
    ReactiveQuerydslPredicateExecutor<BookComment> {

  Flux<BookComment> findAllByBookId(String bookId);
}
