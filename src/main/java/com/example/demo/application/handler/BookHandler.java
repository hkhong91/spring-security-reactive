package com.example.demo.application.handler;

import com.example.demo.application.request.BookCreateRequest;
import com.example.demo.application.request.BookUpdateRequest;
import com.example.demo.application.response.BookResponse;
import com.example.demo.domain.document.Book;
import com.example.demo.domain.document.QBook;
import com.example.demo.domain.repository.BookReactiveMongoRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;
import reactor.util.function.Tuple2;

@Component
@RequiredArgsConstructor
public class BookHandler {

  private final BookReactiveMongoRepository bookReactiveMongoRepository;
  private final ReactiveMongoOperations reactiveMongoOperations;

  public Mono<ServerResponse> getBooksV1(ServerRequest request) {
    // queryParam..
    int page = Integer.parseInt(request.queryParam("page").orElseThrow());
    int size = Integer.parseInt(request.queryParam("size").orElseThrow());
    String keyword = request.queryParam("keyword").orElseThrow();
    Pageable pageable = PageRequest.of(page, size);

    // p341
    Flux<Book> bookFlux = bookReactiveMongoRepository.findManyByTitleRegex(keyword, pageable);
    return ServerResponse.ok().body(bookFlux, Book.class);
  }

  public Mono<ServerResponse> getBooksV2(ServerRequest request) {
    // queryParam..
    int page = Integer.parseInt(request.queryParam("page").orElseThrow());
    int size = Integer.parseInt(request.queryParam("size").orElseThrow());
    String keyword = request.queryParam("keyword").orElseThrow();
    Pageable pageable = PageRequest.of(page, size);

    // p343
    Criteria criteria = Criteria.where("title").regex(keyword);
    Query query = Query.query(criteria).with(pageable);
    Flux<Book> bookFlux = reactiveMongoOperations.find(query, Book.class);
    return ServerResponse.ok().body(bookFlux, Book.class);
  }

  public Mono<ServerResponse> getBooksV3(ServerRequest request) {
    // queryParam..
    String keyword = request.queryParam("keyword").orElseThrow();

    // reactive querydsl not supported pagination
    BooleanExpression where = QBook.book.title.contains(keyword);
    Flux<Book> bookFlux = bookReactiveMongoRepository.findAll(where);
    return ServerResponse.ok().body(bookFlux, Book.class);
  }

  public Mono<ServerResponse> getBook(ServerRequest request) {
    String id = request.pathVariable("id");
    return bookReactiveMongoRepository.findById(id)
        .flatMap(book -> ServerResponse.ok().bodyValue(BookResponse.of(book)));
  }

  public Mono<ServerResponse> createBook(ServerRequest request) {
    return request.bodyToMono(BookCreateRequest.class)
        .flatMap(body -> bookReactiveMongoRepository.save(body.toBook()))
        .flatMap(book -> ServerResponse.ok().bodyValue(BookResponse.of(book)));
  }

  public Mono<ServerResponse> updateBookV1(ServerRequest request) {
    // p336
    String id = request.pathVariable("id");
    return bookReactiveMongoRepository.findById(id)
        .flatMap(book -> request.bodyToMono(BookUpdateRequest.class)
            .flatMap(requestBody -> {
              book.setTitle(requestBody.getTitle());
              book.setAuthors(requestBody.getAuthors());
              book.setPublishedYear(requestBody.getPublishedYear());
              return bookReactiveMongoRepository.save(book);
            }))
        .flatMap(book -> ServerResponse.ok().bodyValue(BookResponse.of(book)));
  }

  public Mono<ServerResponse> updateBookV2(ServerRequest request) {
    // p337
    String id = request.pathVariable("id");
    return Mono.zip(bookReactiveMongoRepository.findById(id), request.bodyToMono(BookUpdateRequest.class))
        .flatMap((Tuple2<Book, BookUpdateRequest> data) -> {
          Book book = data.getT1();
          BookUpdateRequest requestBody = data.getT2();
          book.setTitle(requestBody.getTitle());
          book.setAuthors(requestBody.getAuthors());
          book.setPublishedYear(requestBody.getPublishedYear());
          return bookReactiveMongoRepository.save(book);
        })
        .flatMap(book -> ServerResponse.ok().bodyValue(BookResponse.of(book)));
  }

  public Mono<ServerResponse> updateBookV3(ServerRequest request) {
    // p339
    String id = request.pathVariable("id");
    return Mono.zip(bookReactiveMongoRepository.findById(id), request.bodyToMono(BookUpdateRequest.class))
        .flatMap(TupleUtils.function((book, requestBody) -> {
          book.setTitle(requestBody.getTitle());
          book.setAuthors(requestBody.getAuthors());
          book.setPublishedYear(requestBody.getPublishedYear());
          return bookReactiveMongoRepository.save(book);
        }))
        .flatMap(book -> ServerResponse.ok().bodyValue(BookResponse.of(book)));
  }

  public Mono<ServerResponse> updateBookV4(ServerRequest request) {
    return request.bodyToMono(BookUpdateRequest.class)
        .flatMap(requestBody -> {
          String id = request.pathVariable("id");
          Criteria criteria = Criteria.where("id").is(id);
          Query query = Query.query(criteria);
          Update update = Update.update("title", requestBody.getTitle())
              .set("authors", requestBody.getAuthors())
              .set("publishedYear", requestBody.getPublishedYear())
              .set("updatedAt", LocalDateTime.now());
          return reactiveMongoOperations.findAndModify(query, update, Book.class);
        })
        .flatMap(book -> ServerResponse.ok().bodyValue(BookResponse.of(book)));
  }

  public Mono<ServerResponse> deleteBookV1(ServerRequest request) {
    String id = request.pathVariable("id");
    return ServerResponse.ok().build(bookReactiveMongoRepository.deleteById(id));
  }

  public Mono<ServerResponse> deleteBookV2(ServerRequest request) {
    String id = request.pathVariable("id");
    Criteria criteria = Criteria.where("id").is(id);
    Query query = Query.query(criteria);
    return reactiveMongoOperations.findAndRemove(query, Book.class)
        .flatMap(book -> ServerResponse.ok().bodyValue(BookResponse.of(book)));
  }
}
