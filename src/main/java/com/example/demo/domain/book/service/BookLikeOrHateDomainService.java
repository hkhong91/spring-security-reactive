package com.example.demo.domain.book.service;

import com.example.demo.domain.book.document.BookLikeOrHate;
import com.example.demo.domain.book.repository.BookLikeOrHateRepository;
import com.example.demo.domain.book.value.LikeOrHate;
import com.example.demo.infrastructure.exception.DomainException;
import com.example.demo.infrastructure.exception.DomainMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookLikeOrHateDomainService {

  private final BookLikeOrHateRepository bookLikeOrHateRepository;

  public Mono<BookLikeOrHate> getOne(String likeOrHateId) {
    return bookLikeOrHateRepository.findById(likeOrHateId)
        .switchIfEmpty(Mono.error(new DomainException(DomainMessage.NOT_LIKED_OR_HATED_BOOK)));
  }

  public Mono<BookLikeOrHate> getOneOrDefault(String bookId, String userId) {
    return bookLikeOrHateRepository.findByBookIdAndUserId(bookId, userId)
        .defaultIfEmpty(BookLikeOrHate.none(bookId, userId));
  }

  public Flux<BookLikeOrHate> getList(Set<String> bookIds, String userId) {
    return bookLikeOrHateRepository.findAllByBookIdInAndUserId(bookIds, userId);
  }

  public Mono<Map<String, BookLikeOrHate>> getMap(Set<String> bookIds, String userId) {
    return this.getList(bookIds, userId)
        .collectMap(BookLikeOrHate::getBookId, likeOrHate -> likeOrHate);
  }

  public Mono<BookLikeOrHate> createOne(BookLikeOrHate likeOrHate) {
    return bookLikeOrHateRepository.save(likeOrHate);
  }

  public Mono<Void> removeOne(BookLikeOrHate likeOrHate) {
    return bookLikeOrHateRepository.delete(likeOrHate);
  }

  public Mono<BookLikeOrHate> likeOne(String bookId, String userId) {
    return this.createOne(BookLikeOrHate.like(bookId, userId));
  }

  public Mono<BookLikeOrHate> likeOne(String likeOrHateId) {
    return this.getOne(likeOrHateId)
        .flatMap(hate -> {
          if (hate.liked()) {
            return Mono.error(new DomainException(DomainMessage.ALREADY_LIKED_BOOK));
          }
          hate.setValue(LikeOrHate.LIKE);
          return this.createOne(hate);
        });
  }

  public Mono<Void> unlikeOne(String likeOrHateId) {
    return this.getOne(likeOrHateId)
        .flatMap(like -> {
          if (like.hated()) return Mono.error(new DomainException(DomainMessage.NOT_LIKED_BOOK));
          else return this.removeOne(like);
        });
  }

  public Mono<BookLikeOrHate> hateOne(String bookId, String userId) {
    return this.createOne(BookLikeOrHate.hate(bookId, userId));
  }

  public Mono<BookLikeOrHate> hateOne(String likeOrHateId) {
    return this.getOne(likeOrHateId)
        .flatMap(like -> {
          if (like.hated()) {
            return Mono.error(new DomainException(DomainMessage.ALREADY_HATED_BOOK));
          }
          like.setValue(LikeOrHate.HATE);
          return this.createOne(like);
        });
  }

  public Mono<Void> unhateOne(String likeOrHateId) {
    return this.getOne(likeOrHateId)
        .flatMap(hate -> {
          if (hate.liked()) return Mono.error(new DomainException(DomainMessage.NOT_HATED_BOOK));
          else return this.removeOne(hate);
        });
  }
}
