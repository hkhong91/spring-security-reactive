package com.example.demo.application.book.service;

import com.example.demo.application.book.model.request.BookCategoryRequest;
import com.example.demo.application.book.model.request.BookRequest;
import com.example.demo.application.book.model.response.BookReadResponse;
import com.example.demo.application.book.model.response.BookResponse;
import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.book.document.Book;
import com.example.demo.domain.book.document.sub.Category;
import com.example.demo.domain.book.service.BookDomainService;
import com.example.demo.domain.book.service.BookLikeOrHateDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

  private final BookDomainService bookDomainService;
  private final BookLikeOrHateDomainService bookLikeOrHateDomainService;

  public Mono<BookResponse> getBook(String bookId) {
    return bookDomainService.getOne(bookId)
        .map(BookResponse::of);
  }

  public Mono<BookReadResponse> getBook(String bookId,
                                        AuthUser authUser) {
    String userId = authUser.getId();
    return bookDomainService.getOne(bookId)
        .zipWith(bookLikeOrHateDomainService.getOneOrDefault(bookId, userId))
        .map(TupleUtils.function(BookReadResponse::of));
  }

  public Flux<BookResponse> getBooks() {
    return bookDomainService.getList()
        .map(BookResponse::of);
  }

  public Flux<BookReadResponse> getBooks(AuthUser authUser) {
    String userId = authUser.getId();
    return bookDomainService.getList()
        .collectMap(Book::getId, book -> book)
        .flatMap(bookMap -> bookLikeOrHateDomainService.getMap(bookMap.keySet(), userId)
            .map(likeOrHateMap -> Flux.fromIterable(bookMap.keySet())
                .map(bookId -> BookReadResponse.of(bookMap.get(bookId), likeOrHateMap.get(bookId)))))
        .flatMapMany(response -> response);
  }

  public Mono<BookResponse> createBook(BookRequest request, AuthUser authUser) {
    return bookDomainService.createOne(request.toBook(authUser))
        .map(BookResponse::of);
  }

  public Mono<BookResponse> updateBook(String bookId,
                                       BookRequest request,
                                       AuthUser authUser) {
    return bookDomainService.getOne(bookId)
        .flatMap(book -> {
          book.setTitle(request.getTitle());
          book.setAuthors(request.getAuthors());
          book.setPublishedDate(request.getPublishedDate());
          return bookDomainService.createOne(book);
        })
        .map(BookResponse::of);
  }

  public Mono<BookResponse> addBookCategories(String bookId,
                                              BookCategoryRequest request,
                                              AuthUser authUser) {
    return bookDomainService.getOne(bookId)
        .flatMap(book -> {
          Set<Category> categories = book.getCategories();
          if (CollectionUtils.isEmpty(categories)) book.setCategories(request.toCategories());
          else categories.addAll(request.toCategories());
          return bookDomainService.createOne(book);
        })
        .map(BookResponse::of);
  }

  public Mono<Void> deleteBook(String bookId,
                               AuthUser authUser) {
    return bookDomainService.getOne(bookId)
        .flatMap(bookDomainService::removeOne);
  }
}
