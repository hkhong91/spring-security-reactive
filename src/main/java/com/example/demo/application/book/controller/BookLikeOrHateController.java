package com.example.demo.application.book.controller;

import com.example.demo.application.book.model.response.BookLikeOrHateResponse;
import com.example.demo.application.book.service.BookLikeOrHateService;
import com.example.demo.infrastructure.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Validated
@RequiredArgsConstructor
public class BookLikeOrHateController {

  private final BookLikeOrHateService bookLikeOrHateService;

  @PostMapping("/books/{bookId}/likes")
  public Mono<BookLikeOrHateResponse> likeBook(@PathVariable String bookId,
                                               @AuthenticationPrincipal AuthUser authUser) {
    return bookLikeOrHateService.likeBook(bookId, authUser);
  }

  @PutMapping("/books/{bookId}/likes/{likeOrHateId}")
  public Mono<BookLikeOrHateResponse> likeBook(@PathVariable String bookId,
                                               @PathVariable String likeOrHateId,
                                               @AuthenticationPrincipal AuthUser authUser) {
    return bookLikeOrHateService.likeBook(bookId, likeOrHateId, authUser);
  }

  @DeleteMapping("/books/{bookId}/likes/{likeOrHateId}")
  public Mono<BookLikeOrHateResponse> unlikeBook(@PathVariable String bookId,
                                                 @PathVariable String likeOrHateId,
                                                 @AuthenticationPrincipal AuthUser authUser) {
    return bookLikeOrHateService.unlikeBook(bookId, likeOrHateId, authUser);
  }

  @PostMapping("/books/{bookId}/hates")
  public Mono<BookLikeOrHateResponse> hateBook(@PathVariable String bookId,
                                               @AuthenticationPrincipal AuthUser authUser) {
    return bookLikeOrHateService.hateBook(bookId, authUser);
  }

  @PutMapping("/books/{bookId}/hates/{likeOrHateId}")
  public Mono<BookLikeOrHateResponse> hateBook(@PathVariable String bookId,
                                               @PathVariable String likeOrHateId,
                                               @AuthenticationPrincipal AuthUser authUser) {
    return bookLikeOrHateService.hateBook(bookId, likeOrHateId, authUser);
  }

  @DeleteMapping("/books/{bookId}/hates/{likeOrHateId}")
  public Mono<BookLikeOrHateResponse> unhateBook(@PathVariable String bookId,
                                                 @PathVariable String likeOrHateId,
                                                 @AuthenticationPrincipal AuthUser authUser) {
    return bookLikeOrHateService.unhateBook(bookId, likeOrHateId, authUser);
  }
}
