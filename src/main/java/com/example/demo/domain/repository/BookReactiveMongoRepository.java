package com.example.demo.domain.repository;

import com.example.demo.domain.document.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Flux;

public interface BookReactiveMongoRepository extends ReactiveMongoRepository<Book, String>, ReactiveQuerydslPredicateExecutor<Book> {

  Flux<Book> findManyByTitleRegex(String title, Pageable pageable);
}
