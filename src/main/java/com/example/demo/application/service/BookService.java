package com.example.demo.application.service;

import com.example.demo.application.exception.CustomException;
import com.example.demo.application.exception.CustomMessage;
import com.example.demo.application.model.request.BookRequest;
import com.example.demo.application.model.response.BookResponse;
import com.example.demo.application.security.SigninUser;
import com.example.demo.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

  private final BookRepository bookRepository;

  public Mono<BookResponse> getBook(String bookId) {
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(CustomException.of(CustomMessage.NOT_FOUND_BOOK)))
        .map(BookResponse::of);
  }

  public Mono<BookResponse> getBook(String bookId,
                                    SigninUser signinUser) {
    String userId = signinUser.getId();
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(CustomException.of(CustomMessage.NOT_FOUND_BOOK)))
        .map(book -> BookResponse.ofUser(book, userId));
  }

  public Flux<BookResponse> getBooks() {
    return bookRepository.findAll()
        .map(BookResponse::of);
  }

  public Flux<BookResponse> getBooks(SigninUser signinUser) {
    String userId = signinUser.getId();
    return bookRepository.findAll()
        .map(book -> BookResponse.ofUser(book, userId));
  }

  public Mono<BookResponse> createBook(BookRequest request) {
    return bookRepository.save(request.toBook())
        .map(BookResponse::of);
  }

  public Mono<BookResponse> updateBook(String bookId,
                                       BookRequest request) {
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(CustomException.of(CustomMessage.NOT_FOUND_BOOK)))
        .flatMap(book -> {
          book.setTitle(request.getTitle());
          book.setAuthors(request.getAuthors());
          book.setPublishedDate(request.getPublishedDate());
          return bookRepository.save(book);
        })
        .map(BookResponse::of);
  }

  public Mono<Void> deleteBook(String bookId) {
    return bookRepository.deleteById(bookId);
  }

  public Mono<BookResponse> likeBook(String bookId,
                                     SigninUser signinUser) {
    String userId = signinUser.getId();
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(CustomException.of(CustomMessage.NOT_FOUND_BOOK)))
        .flatMap(book -> {
          book.like(userId);
          return bookRepository.save(book);
        })
        .map(book -> BookResponse.ofUser(book, userId));
  }

  public Mono<BookResponse> hateBook(String bookId,
                                     SigninUser signinUser) {
    String userId = signinUser.getId();
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(CustomException.of(CustomMessage.NOT_FOUND_BOOK)))
        .flatMap(book -> {
          book.hate(userId);
          return bookRepository.save(book);
        })
        .map(book -> BookResponse.ofUser(book, userId));
  }
}
