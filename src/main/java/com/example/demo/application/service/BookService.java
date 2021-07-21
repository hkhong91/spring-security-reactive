package com.example.demo.application.service;

import com.example.demo.application.exception.ServiceException;
import com.example.demo.application.exception.ServiceMessage;
import com.example.demo.application.model.request.BookLikeOrHateRequest;
import com.example.demo.application.model.request.BookRequest;
import com.example.demo.application.model.response.BookResponse;
import com.example.demo.application.security.SigninUser;
import com.example.demo.domain.document.Book;
import com.example.demo.domain.document.BookRead;
import com.example.demo.domain.repository.BookReadRepository;
import com.example.demo.domain.repository.BookRepository;
import com.example.demo.domain.service.BookLikeOrHateDomainService;
import com.example.demo.domain.value.LikeOrHate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

  private final BookRepository bookRepository;
  private final BookReadRepository bookReadRepository;
  private final BookLikeOrHateDomainService bookLikeOrHateDomainService;

  public Mono<BookResponse> getBook(String bookId) {
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(ServiceException.of(ServiceMessage.NOT_FOUND_BOOK)))
        .map(BookResponse::of);
  }

  public Mono<BookResponse> getBook(String bookId,
                                    SigninUser signinUser) {
    String userId = signinUser.getId();
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(ServiceException.of(ServiceMessage.NOT_FOUND_BOOK)))
        .zipWith(bookReadRepository.findByBookIdAndUserId(bookId, userId))
        .map(TupleUtils.function(BookResponse::of));
  }

  public Flux<BookResponse> getBooks() {
    return bookRepository.findAll()
        .switchIfEmpty(Flux.empty())
        .map(BookResponse::of);
  }

  public Flux<BookResponse> getBooks(SigninUser signinUser) {
    String userId = signinUser.getId();
    return bookRepository.findAll()
        .switchIfEmpty(Flux.empty())
        .collectMap(Book::getId, book -> book)
        .flatMap(bookMap -> bookReadRepository.findAllByBookIdInAndUserId(bookMap.keySet(), userId)
            .collectMap(BookRead::getBookId, read -> read)
            .map(readMap -> Flux.fromIterable(bookMap.keySet())
                .map(bookId -> BookResponse.of(bookMap.get(bookId), readMap.get(bookId)))))
        .flatMapMany(response -> response);
  }

  public Mono<BookResponse> createBook(BookRequest request) {
    return bookRepository.save(request.toBook())
        .map(BookResponse::of);
  }

  public Mono<BookResponse> updateBook(String bookId,
                                       BookRequest request) {
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(ServiceException.of(ServiceMessage.NOT_FOUND_BOOK)))
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

  public Mono<BookResponse> likeOrHateBook(String bookId,
                                           BookLikeOrHateRequest request,
                                           SigninUser signinUser) {
    String userId = signinUser.getId();
    LikeOrHate likeOrHate = request.getLikeOrHate();
    return bookLikeOrHateDomainService.modifyBookRead(bookId, userId, likeOrHate)
        .flatMap(read -> bookLikeOrHateDomainService.likeOrHateBook(bookId, read.getLikeOrHate(), likeOrHate)
            .map(book -> BookResponse.of(book, likeOrHate)));
  }
}
