package com.example.demo.application.book.controller;

import com.example.demo.application.book.model.BookLikeOrHateRequest;
import com.example.demo.application.book.model.BookRequest;
import com.example.demo.application.book.model.BookResponse;
import com.example.demo.application.book.service.BookService;
import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.user.value.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping("/books/{bookId}")
  public Mono<BookResponse> getBook(@PathVariable String bookId) {
    return bookService.getBook(bookId);
  }

  @GetMapping("/books/{bookId}/read")
  public Mono<BookResponse> getBook(@PathVariable String bookId,
                                    @AuthenticationPrincipal AuthUser authUser) {
    return bookService.getBook(bookId, authUser);
  }

  @GetMapping("/books")
  public Flux<BookResponse> getBooks() {
    return bookService.getBooks();
  }

  @GetMapping("/books/read")
  public Flux<BookResponse> getBooks(@AuthenticationPrincipal AuthUser authUser) {
    return bookService.getBooks(authUser);
  }

  @PostMapping("/books")
  @PreAuthorize(Authority.Has.SELLER)
  public Mono<BookResponse> createBook(@RequestBody BookRequest request,
                                       @AuthenticationPrincipal AuthUser authUser) {
    return bookService.createBook(request);
  }

  @PatchMapping("/books/{bookId}")
  @PreAuthorize(Authority.Has.SELLER)
  public Mono<BookResponse> updateBook(@PathVariable String bookId,
                                       @RequestBody BookRequest request,
                                       @AuthenticationPrincipal AuthUser authUser) {
    return bookService.updateBook(bookId, request);
  }

  @DeleteMapping("/books/{bookId}")
  @PreAuthorize(Authority.Has.SELLER)
  public Mono<Void> deleteBook(@PathVariable String bookId,
                               @AuthenticationPrincipal AuthUser authUser) {
    return bookService.deleteBook(bookId);
  }

  @PutMapping("/books/{bookId}/like-or-hate")
  public Mono<BookResponse> likeOrHateBook(@PathVariable String bookId,
                                           @RequestBody @Valid BookLikeOrHateRequest request,
                                           @AuthenticationPrincipal AuthUser authUser) {
    return bookService.likeOrHateBook(bookId, request, authUser);
  }
}
