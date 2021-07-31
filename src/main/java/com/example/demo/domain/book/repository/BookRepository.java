package com.example.demo.domain.book.repository;

import com.example.demo.domain.book.document.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveMongoRepository<Book, String>,
    ReactiveQuerydslPredicateExecutor<Book>, BookRepositoryCustom {

  Flux<Book> findManyByTitleRegex(String title, Pageable pageable);
}
