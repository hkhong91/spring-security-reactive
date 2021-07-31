package com.example.demo.application.book.controller;

import com.example.demo.application.book.model.request.BookCategoryRequest;
import com.example.demo.application.book.model.request.BookRequest;
import com.example.demo.application.book.model.response.BookReadResponse;
import com.example.demo.application.book.model.response.BookResponse;
import com.example.demo.application.book.service.BookService;
import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.user.value.Authority;
import com.example.demo.infrastructure.webflux.ClientIp;
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
  public Mono<BookResponse> getBook(@PathVariable String bookId,
                                    @ClientIp String clientIp) {
    return bookService.getBook(bookId);
  }

  @GetMapping("/books/{bookId}/read")
  public Mono<BookReadResponse> getBook(@PathVariable String bookId,
                                        @AuthenticationPrincipal AuthUser authUser,
                                        @ClientIp String clientIp) {
    return bookService.getBook(bookId, authUser);
  }

  @GetMapping("/books")
  public Flux<BookResponse> getBooks() {
    return bookService.getBooks();
  }

  @GetMapping("/books/read")
  public Flux<BookReadResponse> getBooks(@AuthenticationPrincipal AuthUser authUser) {
    return bookService.getBooks(authUser);
  }

  @PostMapping("/books")
  @PreAuthorize(Authority.Has.ADMIN)
  public Mono<BookResponse> createBook(@RequestBody BookRequest request,
                                       @AuthenticationPrincipal AuthUser authUser) {
    return bookService.createBook(request, authUser);
  }

  @PatchMapping("/books/{bookId}")
  @PreAuthorize(Authority.Has.ADMIN)
  public Mono<BookResponse> updateBook(@PathVariable String bookId,
                                       @RequestBody BookRequest request,
                                       @AuthenticationPrincipal AuthUser authUser) {
    return bookService.updateBook(bookId, request, authUser);
  }

  @PutMapping("/books/{bookId}/categories")
  @PreAuthorize(Authority.Has.ADMIN)
  public Mono<BookResponse> addBookCategories(@PathVariable String bookId,
                                              @RequestBody @Valid BookCategoryRequest request,
                                              @AuthenticationPrincipal AuthUser authUser) {
    return bookService.addBookCategories(bookId, request, authUser);
  }

  @DeleteMapping("/books/{bookId}")
  @PreAuthorize(Authority.Has.ADMIN)
  public Mono<Void> deleteBook(@PathVariable String bookId,
                               @AuthenticationPrincipal AuthUser authUser) {
    return bookService.deleteBook(bookId, authUser);
  }
}
