package com.example.demo.application.book.service;

import com.example.demo.application.book.model.BookCommentRequest;
import com.example.demo.application.book.model.BookCommentResponse;
import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.book.repository.BookCommentRepository;
import com.example.demo.infrastructure.exception.ServiceException;
import com.example.demo.infrastructure.exception.ServiceMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookCommentService {

  private final BookCommentRepository bookCommentRepository;

  public Flux<BookCommentResponse> getComments(String bookId) {
    return bookCommentRepository.findAllByBookId(bookId)
        .switchIfEmpty(Flux.empty())
        .map(BookCommentResponse::of);
  }

  public Mono<BookCommentResponse> createComment(String bookId,
                                                 BookCommentRequest request,
                                                 AuthUser authUser) {
    return bookCommentRepository.save(request.toComment(bookId))
        .map(BookCommentResponse::of);
  }

  public Mono<BookCommentResponse> updateComment(String bookId,
                                                 String commentId,
                                                 BookCommentRequest request,
                                                 AuthUser authUser) {
    return bookCommentRepository.findById(commentId)
        .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.NOT_FOUND_BOOK_COMMENT)))
        .flatMap(comment -> {
          comment.verify(bookId, authUser.getId());
          comment.setContent(request.getContent());
          return bookCommentRepository.save(comment);
        })
        .map(BookCommentResponse::of);
  }

  public Mono<Void> deleteComment(String bookId,
                                  String commentId,
                                  AuthUser authUser) {
    return bookCommentRepository.findById(commentId)
        .switchIfEmpty(Mono.error(new ServiceException(ServiceMessage.NOT_FOUND_BOOK_COMMENT)))
        .flatMap(comment -> {
          comment.verify(bookId, authUser.getId());
          return bookCommentRepository.delete(comment);
        });
  }
}
