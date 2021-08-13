package com.example.demo.domain.book.service;

import com.example.demo.domain.book.document.BookReview;
import com.example.demo.domain.book.repository.BookReviewRepository;
import com.example.demo.exception.DomainMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookReviewDomainService {

  private final BookReviewRepository bookReviewRepository;

  public Mono<BookReview> getReview(String reviewId) {
    return bookReviewRepository.findById(reviewId)
        .switchIfEmpty(DomainMessage.NOT_FOUND_BOOK_REVIEW.error());
  }

  public Flux<BookReview> getReviews(String bookId) {
    return bookReviewRepository.findAllByBookId(bookId)
        .switchIfEmpty(Flux.empty());
  }

  public Mono<BookReview> createReview(BookReview review) {
    return bookReviewRepository.save(review);
  }
}
