package com.example.demo.application.model.response;

import com.example.demo.domain.document.Book;
import com.example.demo.domain.value.LikeOrHate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
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
  private final LikeOrHate likeOrHate;
  private final int likeCount;
  private final int hateCount;

  public static BookResponse of(Book book) {
    return of(book, null);
  }

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
        .likeOrHate(likeOrHate)
        .build();
  }

  public static BookResponse ofUser(Book book, String userId) {
    Map<String, LikeOrHate> likeOrHates = book.getLikeOrHates();
    if (Objects.nonNull(likeOrHates)) {
      return of(book, likeOrHates.get(userId));
    } else {
      return of(book);
    }
  }
}
