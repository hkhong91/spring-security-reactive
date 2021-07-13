package com.example.demo.application.controller;

import com.example.demo.application.request.BookRequest;
import com.example.demo.application.response.BookResponse;
import com.example.demo.application.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping("/books/{bookId}")
  public Mono<BookResponse> getBook(@PathVariable String bookId) {
    return bookService.getBook(bookId);
  }

  @GetMapping("/books")
  public Flux<BookResponse> getBooks() {
    return bookService.getBooks();
  }

  @PostMapping("/books")
  public Mono<BookResponse> createBook(@RequestBody BookRequest request) {
    return bookService.createBook(request);
  }

  @PutMapping("/books/{bookId}")
  public Mono<BookResponse> replaceBook(@PathVariable String bookId,
                                       @RequestBody BookRequest request) {
    return bookService.replaceBook(bookId, request);
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
}
