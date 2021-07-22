package com.example.demo.application.book.controller;

import com.example.demo.application.book.model.BookReviewRequest;
import com.example.demo.application.book.model.BookReviewResponse;
import com.example.demo.application.book.service.BookReviewService;
import com.example.demo.application.user.security.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookReviewController {

  private final BookReviewService bookReviewService;

  @GetMapping("/books/{bookId}/reviews")
  public Flux<BookReviewResponse> getReviews(@PathVariable String bookId) {
    return bookReviewService.getReviews(bookId);
  }

  @PostMapping("/books/{bookId}/reviews")
  public Mono<BookReviewResponse> createReview(@PathVariable String bookId,
                                               @RequestBody BookReviewRequest request,
                                               @AuthenticationPrincipal AuthUser authUser) {
    return bookReviewService.createReview(bookId, request, authUser);
  }

  @PatchMapping("/books/{bookId}/reviews/{reviewId}")
  public Mono<BookReviewResponse> updateReview(@PathVariable String bookId,
                                               @PathVariable String reviewId,
                                               @RequestBody BookReviewRequest request,
                                               @AuthenticationPrincipal AuthUser authUser) {
    return bookReviewService.updateReview(bookId, reviewId, request, authUser);
  }

  @DeleteMapping("/books/{bookId}/reviews/{reviewId}")
  public Mono<Void> deleteReview(@PathVariable String bookId,
                                 @PathVariable String reviewId,
                                 @AuthenticationPrincipal AuthUser authUser) {
    return bookReviewService.deleteReview(bookId, reviewId, authUser);
  }
}
