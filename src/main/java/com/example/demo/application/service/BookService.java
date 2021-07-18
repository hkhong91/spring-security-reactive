package com.example.demo.application.service;

import com.example.demo.application.exception.CustomException;
import com.example.demo.application.exception.CustomMessage;
import com.example.demo.application.request.BookHitRequest;
import com.example.demo.application.request.BookRequest;
import com.example.demo.application.response.BookHitResponse;
import com.example.demo.application.response.BookResponse;
import com.example.demo.application.security.SigninUser;
import com.example.demo.domain.repository.BookHitReactiveMongoRepository;
import com.example.demo.domain.repository.BookReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

  private final BookReactiveMongoRepository bookReactiveMongoRepository;
  private final BookHitReactiveMongoRepository bookHitReactiveMongoRepository;

  public Mono<BookResponse> getBook(String bookId) {
    return bookReactiveMongoRepository.findById(bookId)
        .switchIfEmpty(Mono.error(CustomException.of(CustomMessage.NOT_FOUND_BOOK)))
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

  public Mono<BookResponse> updateBook(String bookId,
                                       BookRequest request) {
    return bookReactiveMongoRepository.findById(bookId)
        .switchIfEmpty(Mono.error(CustomException.of(CustomMessage.NOT_FOUND_BOOK)))
        .flatMap(book -> {
          book.setTitle(request.getTitle());
          book.setAuthors(request.getAuthors());
          book.setPublishedDate(request.getPublishedDate());
          return bookReactiveMongoRepository.save(book);
        })
        .map(BookResponse::of);
  }

  public Mono<Void> deleteBook(String bookId) {
    return bookReactiveMongoRepository.deleteById(bookId);
  }

  public Mono<BookHitResponse> hitBook(String bookId,
                                       BookHitRequest request,
                                       SigninUser signinUser) {
    return bookHitReactiveMongoRepository.findByBookIdAndUserId(bookId, signinUser.getId())
        .flatMap(hit -> {
          hit.setLikeOrHate(request.getLikeOrHate());
          return bookHitReactiveMongoRepository.save(hit);
        })
        .switchIfEmpty(bookHitReactiveMongoRepository.save(request.toBookHit(bookId, signinUser.getId())))
        .map(BookHitResponse::of);
  }
}
