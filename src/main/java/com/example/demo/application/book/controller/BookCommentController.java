package com.example.demo.application.book.controller;

import com.example.demo.application.book.model.BookCommentRequest;
import com.example.demo.application.book.model.BookCommentResponse;
import com.example.demo.application.book.service.BookCommentService;
import com.example.demo.application.user.security.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookCommentController {

  private final BookCommentService bookCommentService;

  @GetMapping("/books/{bookId}/comments")
  public Flux<BookCommentResponse> getComments(@PathVariable String bookId) {
    return bookCommentService.getComments(bookId);
  }

  @PostMapping("/books/{bookId}/comments")
  public Mono<BookCommentResponse> createComment(@PathVariable String bookId,
                                                 @RequestBody BookCommentRequest request,
                                                 @AuthenticationPrincipal AuthUser authUser) {
    return bookCommentService.createComment(bookId, request, authUser);
  }

  @PatchMapping("/books/{bookId}/comments/{commentId}")
  public Mono<BookCommentResponse> updateComment(@PathVariable String bookId,
                                                 @PathVariable String commentId,
                                                 @RequestBody BookCommentRequest request,
                                                 @AuthenticationPrincipal AuthUser authUser) {
    return bookCommentService.updateComment(bookId, commentId, request, authUser);
  }

  @DeleteMapping("/books/{bookId}/comments/{commentId}")
  public Mono<Void> deleteComment(@PathVariable String bookId,
                                  @PathVariable String commentId,
                                  @AuthenticationPrincipal AuthUser authUser) {
    return bookCommentService.deleteComment(bookId, commentId, authUser);
  }
}
