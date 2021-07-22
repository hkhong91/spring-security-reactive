package com.example.demo.domain.book.service;

import com.example.demo.domain.book.document.Book;
import com.example.demo.domain.book.document.BookRead;
import com.example.demo.domain.book.value.LikeOrHate;
import com.example.demo.infrastructure.exception.ServiceException;
import com.example.demo.infrastructure.exception.ServiceMessage;
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
public class BookLikeOrHateDomainService {

  private final ReactiveMongoTemplate mongoTemplate;

  public Mono<BookRead> modifyBookRead(String bookId, String userId, LikeOrHate likeOrHate) {
    Query query = Query.query(Criteria.where("bookId").is(bookId))
        .addCriteria(Criteria.where("userId").is(userId));
    Update update = new Update().set("likeOrHate", likeOrHate);
    FindAndModifyOptions options = FindAndModifyOptions.options().upsert(true);
    return mongoTemplate.findAndModify(query, update, options, BookRead.class)
        .defaultIfEmpty(BookRead.of(bookId, userId));
  }

  public Mono<Book> likeOrHateBook(String bookId, LikeOrHate current, LikeOrHate request) {
    Update update = new Update();
    switch (current) {
      case LIKE:
        switch (request) {
          case LIKE:
            return Mono.error(new ServiceException(ServiceMessage.SAME_VALUE));
          case HATE:
            update.inc("likeCount", -1).inc("hateCount", 1);
            break;
          case NONE:
            update.inc("likeCount", -1);
            break;
        }
        break;
      case HATE:
        switch (request) {
          case LIKE:
            update.inc("likeCount", 1).inc("hateCount", -1);
            break;
          case HATE:
            return Mono.error(new ServiceException(ServiceMessage.SAME_VALUE));
          case NONE:
            update.inc("hateCount", -1);
            break;
        }
        break;
      case NONE:
        switch (request) {
          case LIKE:
            update.inc("likeCount", 1);
            break;
          case HATE:
            update.inc("hateCount", 1);
            break;
          case NONE:
            return Mono.error(new ServiceException(ServiceMessage.SAME_VALUE));
        }
        break;
    }
    Query query = Query.query(Criteria.where("id").is(bookId));
    FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);
    return mongoTemplate.findAndModify(query, update, options, Book.class);
  }
}
