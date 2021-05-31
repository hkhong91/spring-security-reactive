package com.example.demo.application.request;

import com.example.demo.domain.document.Book;
import java.util.Set;
import lombok.Getter;

@Getter
public class BookCreateRequest {

  private String title;
  private Set<String> authors;
  private int publishedYear;

  public Book toBook() {
    return Book.builder()
        .title(this.title)
        .authors(this.authors)
        .publishedYear(this.publishedYear)
        .build();
  }
}
