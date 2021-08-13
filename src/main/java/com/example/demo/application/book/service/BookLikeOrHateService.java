package com.example.demo.application.book.service;

import com.example.demo.application.book.model.response.BookLikeOrHateResponse;
import com.example.demo.domain.book.service.BookAggregationDomainService;
import com.example.demo.domain.book.service.BookLikeOrHateDomainService;
import com.example.demo.infrastructure.security.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookLikeOrHateService {

  private final BookLikeOrHateDomainService bookLikeOrHateDomainService;
  private final BookAggregationDomainService bookAggregationDomainService;

  public Mono<BookLikeOrHateResponse> likeBook(String bookId,
                                               AuthUser authUser) {
    return bookLikeOrHateDomainService.likeOne(bookId, authUser.getId())
        .doOnNext(like -> bookAggregationDomainService.like(bookId).subscribe())
        .map(BookLikeOrHateResponse::of);
  }

  public Mono<BookLikeOrHateResponse> likeBook(String bookId,
                                               String likeOrHateId,
                                               AuthUser authUser) {
    return bookLikeOrHateDomainService.likeOne(likeOrHateId)
        .doOnNext(likeOrHate -> bookAggregationDomainService.likeAndUnhate(bookId).subscribe())
        .map(BookLikeOrHateResponse::of);
  }

  public Mono<BookLikeOrHateResponse> unlikeBook(String bookId,
                                                 String likeOrHateId,
                                                 AuthUser authUser) {
    return bookLikeOrHateDomainService.unlikeOne(likeOrHateId)
        .then(bookAggregationDomainService.unlike(bookId))
        .map(result -> BookLikeOrHateResponse.empty(bookId));
  }

  public Mono<BookLikeOrHateResponse> hateBook(String bookId,
                                               AuthUser authUser) {
    return bookLikeOrHateDomainService.hateOne(bookId, authUser.getId())
        .doOnNext(hate -> bookAggregationDomainService.hate(bookId).subscribe())
        .map(BookLikeOrHateResponse::of);
  }

  public Mono<BookLikeOrHateResponse> hateBook(String bookId,
                                               String likeOrHateId,
                                               AuthUser authUser) {
    return bookLikeOrHateDomainService.hateOne(likeOrHateId)
        .doOnNext(likeOrHate -> bookAggregationDomainService.hateAndUnlike(bookId).subscribe())
        .map(BookLikeOrHateResponse::of);
  }

  public Mono<BookLikeOrHateResponse> unhateBook(String bookId,
                                                 String likeOrHateId,
                                                 AuthUser authUser) {
    return bookLikeOrHateDomainService.unhateOne(likeOrHateId)
        .then(bookAggregationDomainService.unhate(bookId))
        .map(result -> BookLikeOrHateResponse.empty(bookId));
  }
}
