package com.example.demo.domain.book.service;

import com.example.demo.domain.book.document.Book;
import com.example.demo.domain.book.document.BookRead;
import com.example.demo.domain.book.value.LikeOrHate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookDomainService {

  private final ReactiveMongoTemplate mongoTemplate;

  public Mono<BookRead> modifyLikeOrHate(String bookId, String userId, LikeOrHate likeOrHate) {
    Query query = Query.query(Criteria.where("bookId").is(bookId))
        .addCriteria(Criteria.where("userId").is(userId));
    Update update = new Update().set("likeOrHate", likeOrHate);
    FindAndModifyOptions options = FindAndModifyOptions.options().upsert(true);
    return mongoTemplate.findAndModify(query, update, options, BookRead.class)
        .defaultIfEmpty(BookRead.of(bookId, userId));
  }

  public Mono<Book> increaseLikeOrHateCount(String bookId, LikeOrHate current, LikeOrHate request) {
    Update update = current.getIncrementUpdate(request);
    Query query = Query.query(Criteria.where("id").is(bookId));
    FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);
    return mongoTemplate.findAndModify(query, update, options, Book.class);
  }
}
