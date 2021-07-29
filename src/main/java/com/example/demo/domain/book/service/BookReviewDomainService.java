package com.example.demo.domain.book.service;

import com.example.demo.domain.book.document.Book;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookReviewDomainService {

  private final ReactiveMongoTemplate mongoTemplate;

  public Mono<UpdateResult> increaseCount(String bookId, int inc) {
    Query query = Query.query(Criteria.where("id").is(bookId));
    Update update = new Update().inc("aggregation.reviewCount", inc);
    return mongoTemplate.upsert(query, update, Book.class);
  }
}
