package com.example.demo.application.book.service;

import com.example.demo.application.book.model.request.BookReviewRequest;
import com.example.demo.application.book.model.response.BookReviewResponse;
import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.book.service.BookAggregationDomainService;
import com.example.demo.domain.book.service.BookDomainService;
import com.example.demo.domain.book.service.BookReviewDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookReviewService {

  private final BookDomainService bookDomainService;
  private final BookReviewDomainService bookReviewDomainService;
  private final BookAggregationDomainService bookAggregationDomainService;

  public Flux<BookReviewResponse> getReviews(String bookId) {
    return bookReviewDomainService.getReviews(bookId)
        .map(BookReviewResponse::of);
  }

  public Mono<BookReviewResponse> createReview(String bookId,
                                               BookReviewRequest request,
                                               AuthUser authUser) {
    return bookDomainService.getOne(bookId)
        .flatMap(book -> bookReviewDomainService.createReview(request.toReview(book.getId(), authUser)))
        .doOnNext(review -> bookAggregationDomainService.review(bookId, 1).subscribe())
        .map(BookReviewResponse::of);
  }

  public Mono<BookReviewResponse> updateReview(String bookId,
                                               String reviewId,
                                               BookReviewRequest request,
                                               AuthUser authUser) {
    return bookReviewDomainService.getReview(reviewId)
        .flatMap(review -> {
          review.verify(bookId, authUser.getId());
          review.setContent(request.getContent());
          return bookReviewDomainService.createReview(review);
        })
        .map(BookReviewResponse::of);
  }

  public Mono<BookReviewResponse> deleteReview(String bookId,
                                               String reviewId,
                                               AuthUser authUser) {
    return bookReviewDomainService.getReview(reviewId)
        .flatMap(review -> {
          review.verify(bookId, authUser.getId());
          review.setDeleted(true);
          return bookReviewDomainService.createReview(review);
        })
        .map(BookReviewResponse::of);
  }
}
