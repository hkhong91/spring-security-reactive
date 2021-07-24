package com.example.demo.application.book.service;

import com.example.demo.application.book.model.BookReviewRequest;
import com.example.demo.application.book.model.BookReviewResponse;
import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.book.repository.BookRepository;
import com.example.demo.domain.book.repository.BookReviewRepository;
import com.example.demo.domain.book.service.BookReviewDomainService;
import com.example.demo.infrastructure.exception.ServiceException;
import com.example.demo.infrastructure.exception.ServiceMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookReviewService {

  private final BookRepository bookRepository;
  private final BookReviewRepository bookReviewRepository;
  private final BookReviewDomainService bookReviewDomainService;

  public Flux<BookReviewResponse> getReviews(String bookId) {
    return bookReviewRepository.findAllByBookId(bookId)
        .switchIfEmpty(Flux.empty())
        .map(BookReviewResponse::of);
  }

  public Mono<BookReviewResponse> createReview(String bookId,
                                               BookReviewRequest request,
                                               AuthUser authUser) {
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.NOT_FOUND_BOOK)))
        .flatMap(book -> bookReviewRepository.save(request.toReview(book.getId(), authUser)))
        .doOnNext(review -> bookReviewDomainService.increaseCount(bookId, 1).subscribe())
        .map(BookReviewResponse::of);
  }

  public Mono<BookReviewResponse> updateReview(String bookId,
                                               String reviewId,
                                               BookReviewRequest request,
                                               AuthUser authUser) {
    return bookReviewRepository.findById(reviewId)
        .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.NOT_FOUND_BOOK_REVIEW)))
        .flatMap(review -> {
          review.verify(bookId, authUser.getId());
          review.setContent(request.getContent());
          return bookReviewRepository.save(review);
        })
        .map(BookReviewResponse::of);
  }

  public Mono<BookReviewResponse> deleteReview(String bookId,
                                               String reviewId,
                                               AuthUser authUser) {
    return bookReviewRepository.findById(reviewId)
        .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.NOT_FOUND_BOOK_REVIEW)))
        .flatMap(review -> {
          review.verify(bookId, authUser.getId());
          review.setDeleted(true);
          return bookReviewRepository.save(review);
        })
        .map(BookReviewResponse::of);
  }
}
