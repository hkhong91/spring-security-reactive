package com.example.demo.application.service;

import com.example.demo.application.exception.CustomException;
import com.example.demo.application.exception.CustomMessage;
import com.example.demo.application.request.BookReadRequest;
import com.example.demo.application.request.BookRequest;
import com.example.demo.application.response.BookDetailResponse;
import com.example.demo.application.response.BookReadResponse;
import com.example.demo.application.response.BookResponse;
import com.example.demo.application.security.SigninUser;
import com.example.demo.domain.document.Book;
import com.example.demo.domain.document.BookRead;
import com.example.demo.domain.repository.BookReadRepository;
import com.example.demo.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.function.TupleUtils.function;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

  private final BookRepository bookRepository;
  private final BookReadRepository bookReadRepository;

  public Mono<BookDetailResponse> getBook(String bookId) {
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(CustomException.of(CustomMessage.NOT_FOUND_BOOK)))
        .map(BookDetailResponse::of);
  }

  public Mono<BookDetailResponse> getBook(String bookId,
                                          SigninUser signinUser) {
    String userId = signinUser.getId();
    Mono<Book> bookMono = bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(CustomException.of(CustomMessage.NOT_FOUND_BOOK)));
    Mono<BookRead> readMono = bookReadRepository.findByBookIdAndUserId(bookId, userId)
        .defaultIfEmpty(BookRead.of(bookId, userId));
    return Mono.zip(bookMono, readMono).map(function(BookDetailResponse::of));
  }

  public Flux<BookResponse> getBooks() {
    return bookRepository.findAll()
        .map(BookResponse::of);
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

  public Mono<BookReadResponse> readBook(String bookId,
                                        BookReadRequest request,
                                        SigninUser signinUser) {
    String userId = signinUser.getId();
    return bookReadRepository.findByBookIdAndUserId(bookId, userId)
        .flatMap(read -> {
          read.setLikeOrHate(request.getLikeOrHate());
          return bookReadRepository.save(read);
        })
        .switchIfEmpty(bookReadRepository.save(request.toRead(bookId, userId)))
        .map(BookReadResponse::of);
  }
}
