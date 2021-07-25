package com.example.demo.application.book.model;

import com.example.demo.domain.book.document.Book;
import com.example.demo.domain.book.document.BookRead;
import com.example.demo.domain.book.value.LikeOrHate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@SuperBuilder
public class BookResponse {

  private final String id;
  private final String title;
  private final Set<String> authors;
  private final LocalDate publishedDate;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final int likeCount;
  private final int hateCount;
  private final int reviewCount;
  private final LikeOrHate likeOrHate;

  public static BookResponse of(Book book, LikeOrHate likeOrHate) {
    return BookResponse.builder()
        .id(book.getId())
        .title(book.getTitle())
        .authors(book.getAuthors())
        .publishedDate(book.getPublishedDate())
        .createdAt(book.getCreatedAt())
        .updatedAt(book.getUpdatedAt())
        .likeCount(book.getLikeCount())
        .hateCount(book.getHateCount())
        .reviewCount(book.getReviewCount())
        .likeOrHate(likeOrHate)
        .build();
  }

  public static BookResponse of(Book book) {
    return of(book, LikeOrHate.NONE);
  }

  public static BookResponse of(Book book, BookRead read) {
    if (Objects.nonNull(read)) {
      return of(book, read.getLikeOrHate());
    } else {
      return of(book);
    }
  }
}