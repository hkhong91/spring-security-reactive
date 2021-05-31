package com.example.demo.application.response;

import com.example.demo.domain.document.Book;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookResponse {

  private final String id;
  private final String title;
  private final Set<String> authors;
  private final int publishedYear;
  private final LocalDateTime createdAt;

  public static BookResponse of(Book book) {
    return BookResponse.builder()
        .id(book.getId())
        .title(book.getTitle())
        .authors(book.getAuthors())
        .publishedYear(book.getPublishedYear())
        .createdAt(book.getCreatedAt())
        .build();
  }
}