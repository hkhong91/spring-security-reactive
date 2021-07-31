package com.example.demo.domain.book.repository;

import com.example.demo.domain.book.document.Book;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

  private final ReactiveMongoTemplate mongoTemplate;

  @Override
  public Mono<UpdateResult> upsertById(String bookId, Update update) {
    Query query = Query.query(Criteria.where(Book.ID).is(bookId));
    return mongoTemplate.upsert(query, update, Book.class);
  }
}
