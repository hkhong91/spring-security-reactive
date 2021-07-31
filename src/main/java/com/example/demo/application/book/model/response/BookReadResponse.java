package com.example.demo.application.book.model.response;

import com.example.demo.domain.book.document.Book;
import com.example.demo.domain.book.document.BookLikeOrHate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@SuperBuilder
public class BookReadResponse extends BookResponse {

  private final BookLikeOrHateResponse likeOrHate;

  public static BookReadResponse of(Book book, BookLikeOrHate likeOrHate) {
    return BookReadResponse.builder()
        .id(book.getId())
        .title(book.getTitle())
        .authors(book.getAuthors())
        .categories(book.getCategories())
        .publishedDate(book.getPublishedDate())
        .createdAt(book.getCreatedAt())
        .updatedAt(book.getUpdatedAt())
        .aggregation(book.getAggregation())
        .likeOrHate(Objects.isNull(likeOrHate) ?
            BookLikeOrHateResponse.empty(book.getId()) :
            BookLikeOrHateResponse.of(likeOrHate))
        .build();
  }
}
