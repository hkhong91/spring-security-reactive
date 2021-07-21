package com.example.demo.application.controller;

import com.example.demo.application.model.request.BookLikeOrHateRequest;
import com.example.demo.application.model.request.BookRequest;
import com.example.demo.application.model.response.BookResponse;
import com.example.demo.application.security.JWTService;
import com.example.demo.application.security.SigninUser;
import com.example.demo.application.service.BookService;
import com.example.demo.domain.value.LikeOrHate;
import com.mongodb.client.result.UpdateResult;
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
  public Mono<BookResponse> getBook(@PathVariable String bookId,
                                    @RequestHeader(value = "Authorization", required = false) String authorization) {
    if (Objects.nonNull(authorization)) {
      SigninUser signinUser = jwtService.verify(authorization);
      return bookService.getBook(bookId, signinUser);
    } else {
      return bookService.getBook(bookId);
    }
  }

  @GetMapping("/books")
  public Flux<BookResponse> getBooks(@RequestHeader(value = "Authorization", required = false) String authorization) {
    if (Objects.nonNull(authorization)) {
      SigninUser signinUser = jwtService.verify(authorization);
      return bookService.getBooks(signinUser);
    } else {
      return bookService.getBooks();
    }
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

  @PutMapping("/books/{bookId}/like-or-hate")
  public Mono<BookResponse> likeOrHateBook(@PathVariable String bookId,
                                     @RequestHeader(value = "Authorization") String authorization,
                                     @RequestBody BookLikeOrHateRequest request) {
    SigninUser signinUser = jwtService.verify(authorization);
    return bookService.likeOrHateBook(bookId, request, signinUser);
  }
}
