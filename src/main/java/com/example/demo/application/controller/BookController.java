package com.example.demo.application.controller;

import com.example.demo.application.request.BookReadRequest;
import com.example.demo.application.request.BookRequest;
import com.example.demo.application.response.BookDetailResponse;
import com.example.demo.application.response.BookReadResponse;
import com.example.demo.application.response.BookResponse;
import com.example.demo.application.security.JWTService;
import com.example.demo.application.security.SigninUser;
import com.example.demo.application.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final JWTService jwtService;
  private final BookService bookService;

  @GetMapping("/books/{bookId}")
  public Mono<BookDetailResponse> getBook(@PathVariable String bookId,
                                          @RequestHeader(value = "Authorization", required = false) String authorization) {
    if (Objects.nonNull(authorization)) {
      SigninUser signinUser = jwtService.verify(authorization);
      return bookService.getBook(bookId, signinUser);
    } else {
      return bookService.getBook(bookId);
    }
  }

  @GetMapping("/books")
  public Flux<BookResponse> getBooks() {
    return bookService.getBooks();
  }

  @PostMapping("/books")
  public Mono<BookResponse> createBook(@RequestBody BookRequest request) {
    return bookService.createBook(request);
  }

  @PatchMapping("/books/{bookId}")
  public Mono<BookResponse> updateBook(@PathVariable String bookId,
                                       @RequestBody BookRequest request) {
    return bookService.updateBook(bookId, request);
  }

  @DeleteMapping("/books/{bookId}")
  public Mono<Void> deleteBook(@PathVariable String bookId) {
    return bookService.deleteBook(bookId);
  }

  @PutMapping("/books/{bookId}/read")
  public Mono<BookReadResponse> readBook(@PathVariable String bookId,
                                        @RequestHeader(value = "Authorization") String authorization,
                                        @RequestBody BookReadRequest request) {
    SigninUser signinUser = jwtService.verify(authorization);
    return bookService.readBook(bookId, request, signinUser);
  }
}
