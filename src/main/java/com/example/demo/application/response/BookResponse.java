package com.example.demo.application.response;

import com.example.demo.domain.document.Book;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class BookResponse {

  private final String id;
  private final String title;
  private final Set<String> authors;
  private final LocalDate publishedDate;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public static BookResponse of(Book book) {
    return BookResponse.builder()
        .id(book.getId())
        .title(book.getTitle())
        .authors(book.getAuthors())
        .publishedDate(book.getPublishedDate())
        .createdAt(book.getCreatedAt())
        .updatedAt(book.getUpdatedAt())
        .build();
  }
}
