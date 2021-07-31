package com.example.demo.domain.book.service;

import com.example.demo.domain.book.document.Book;
import com.example.demo.domain.book.repository.BookRepository;
import com.example.demo.infrastructure.exception.DomainMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookDomainService {

  private final BookRepository bookRepository;

  public Mono<Book> getOne(String bookId) {
    return bookRepository.findById(bookId)
        .switchIfEmpty(DomainMessage.NOT_FOUND_BOOK.error());
  }

  public Flux<Book> getList() {
    return bookRepository.findAll()
        .switchIfEmpty(Flux.empty());
  }

  public Mono<Book> createOne(Book book) {
    return bookRepository.save(book);
  }

  public Mono<Void> removeOne(Book book) {
    return bookRepository.delete(book);
  }
}
