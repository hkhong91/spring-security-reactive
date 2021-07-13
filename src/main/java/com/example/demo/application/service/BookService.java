package com.example.demo.application.service;

import com.example.demo.application.request.BookRequest;
import com.example.demo.application.response.BookResponse;
import com.example.demo.domain.repository.BookReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookReactiveMongoRepository bookReactiveMongoRepository;

  public Mono<BookResponse> getBook(String id) {
    return bookReactiveMongoRepository.findById(id)
        .map(BookResponse::of);
  }

  public Flux<BookResponse> getBooks() {
    return bookReactiveMongoRepository.findAll()
        .map(BookResponse::of);
  }

  public Mono<BookResponse> createBook(BookRequest request) {
    return bookReactiveMongoRepository.save(request.toBook())
        .map(BookResponse::of);
  }

  public Mono<BookResponse> replaceBook(String bookId, BookRequest request) {
    return bookReactiveMongoRepository.save(request.toBook(bookId))
        .map(BookResponse::of);
  }

  public Mono<BookResponse> updateBook(String bookId, BookRequest request) {
    return bookReactiveMongoRepository.findById(bookId)
        .flatMap(book -> {
          book.setTitle(request.getTitle());
          book.setAuthors(request.getAuthors());
          book.setPublishedYear(request.getPublishedYear());
          return bookReactiveMongoRepository.save(book);
        })
        .map(BookResponse::of);
  }

  public Mono<Void> deleteBook(String bookId) {
    return bookReactiveMongoRepository.deleteById(bookId);
  }
}
