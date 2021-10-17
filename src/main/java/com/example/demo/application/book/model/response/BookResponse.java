package com.example.demo.application.book.model.response;

import com.example.demo.domain.book.document.Book;
import com.example.demo.domain.book.document.sub.Aggregation;
import com.example.demo.domain.book.document.sub.Category;
import com.example.demo.domain.book.document.sub.Creator;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@SuperBuilder
public class BookResponse {

  private final String id;
  private final String title;
  private final Set<String> authors;
  private final Set<Category> categories;
  private final LocalDate publishedDate;
  private final Creator creator;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final Aggregation aggregation;

  public static BookResponse of(Book book) {
    return BookResponse.builder()
        .id(book.getId())
        .title(book.getTitle())
        .authors(book.getAuthors())
        .categories(book.getCategories())
        .publishedDate(book.getPublishedDate())
        .creator(book.getCreator())
        .createdAt(book.getCreatedAt())
        .updatedAt(book.getUpdatedAt())
        .aggregation(book.getAggregation())
        .build();
  }
}
