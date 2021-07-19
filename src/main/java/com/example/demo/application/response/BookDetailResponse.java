package com.example.demo.application.response;

import com.example.demo.domain.document.Book;
import com.example.demo.domain.document.BookRead;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@SuperBuilder
public class BookDetailResponse extends BookResponse {

  private final BookReadResponse read;

  public static BookDetailResponse of(Book book) {
    return of(book, null);
  }

  public static BookDetailResponse of(Book book, BookRead read) {
    return BookDetailResponse.builder()
        .id(book.getId())
        .title(book.getTitle())
        .authors(book.getAuthors())
        .publishedDate(book.getPublishedDate())
        .createdAt(book.getCreatedAt())
        .updatedAt(book.getUpdatedAt())
        .read(Objects.nonNull(read) ? BookReadResponse.of(read) : null)
        .build();
  }
}
