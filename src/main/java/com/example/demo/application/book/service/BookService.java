package com.example.demo.application.book.service;

import com.example.demo.application.book.model.BookLikeOrHateRequest;
import com.example.demo.application.book.model.BookRequest;
import com.example.demo.application.book.model.BookResponse;
import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.book.document.Book;
import com.example.demo.domain.book.document.BookRead;
import com.example.demo.domain.book.repository.BookReadRepository;
import com.example.demo.domain.book.repository.BookRepository;
import com.example.demo.domain.book.service.BookDomainService;
import com.example.demo.domain.book.value.LikeOrHate;
import com.example.demo.infrastructure.exception.ServiceException;
import com.example.demo.infrastructure.exception.ServiceMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

  private final BookRepository bookRepository;
  private final BookReadRepository bookReadRepository;
  private final BookDomainService bookDomainService;

  public Mono<BookResponse> getBook(String bookId) {
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.NOT_FOUND_BOOK)))
        .map(BookResponse::of);
  }

  public Mono<BookResponse> getBook(String bookId,
                                    AuthUser authUser) {
    String userId = authUser.getId();
    return bookRepository.findById(bookId)
        .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.NOT_FOUND_BOOK)))
        .zipWith(bookReadRepository.findByBookIdAndUserId(bookId, userId))
        .map(TupleUtils.function(BookResponse::of));
  }

  public Flux<BookResponse> getBooks() {
    return bookRepository.findAll()
        .switchIfEmpty(Flux.empty())
        .map(BookResponse::of);
  }

  public Flux<BookResponse> getBooks(AuthUser authUser) {
    String userId = authUser.getId();
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
        .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.NOT_FOUND_BOOK)))
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

  @Transactional
  public Mono<BookResponse> likeOrHateBook(String bookId,
                                           BookLikeOrHateRequest request,
                                           AuthUser authUser) {
    String userId = authUser.getId();
    LikeOrHate likeOrHate = request.getLikeOrHate();
    return bookDomainService.modifyLikeOrHate(bookId, userId, likeOrHate)
        .flatMap(read -> bookDomainService.increaseLikeOrHateCount(bookId, read.getLikeOrHate(), likeOrHate)
            .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.NOT_FOUND_BOOK)))
            .map(book -> BookResponse.of(book, likeOrHate)));
  }
}
