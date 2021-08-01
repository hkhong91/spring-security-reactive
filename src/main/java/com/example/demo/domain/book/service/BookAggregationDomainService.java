package com.example.demo.domain.book.service;

import com.example.demo.domain.book.document.sub.Aggregation;
import com.example.demo.domain.book.repository.BookRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookAggregationDomainService {

  private final BookRepository bookRepository;

  public Mono<UpdateResult> like(String bookId) {
    Update update = new Update()
        .inc(Aggregation.LIKE_COUNT, 1);
    return bookRepository.upsertById(bookId, update);
  }

  public Mono<UpdateResult> likeAndUnhate(String bookId) {
    Update update = new Update()
        .inc(Aggregation.LIKE_COUNT, 1)
        .inc(Aggregation.HATE_COUNT, -1);
    return bookRepository.upsertById(bookId, update);
  }

  public Mono<UpdateResult> unlike(String bookId) {
    Update update = new Update()
        .inc(Aggregation.LIKE_COUNT, -1);
    return bookRepository.upsertById(bookId, update);
  }

  public Mono<UpdateResult> hate(String bookId) {
    Update update = new Update()
        .inc(Aggregation.HATE_COUNT, 1);
    return bookRepository.upsertById(bookId, update);
  }

  public Mono<UpdateResult> hateAndUnlike(String bookId) {
    Update update = new Update()
        .inc(Aggregation.HATE_COUNT, 1)
        .inc(Aggregation.LIKE_COUNT, -1);
    return bookRepository.upsertById(bookId, update);
  }

  public Mono<UpdateResult> unhate(String bookId) {
    Update update = new Update()
        .inc(Aggregation.HATE_COUNT, -1);
    return bookRepository.upsertById(bookId, update);
  }

  public Mono<UpdateResult> review(String bookId) {
    Update update = new Update().inc(Aggregation.REVIEW_COUNT, 1);
    return bookRepository.upsertById(bookId, update);
  }

  public Mono<UpdateResult> hit(String bookId, int inc) {
    Update update = new Update().inc(Aggregation.HIT_COUNT, inc);
    return bookRepository.upsertById(bookId, update);
  }
}
