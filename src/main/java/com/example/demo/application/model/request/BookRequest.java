package com.example.demo.application.model.request;

import com.example.demo.domain.document.Book;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Getter
public class BookRequest {

  private String title;
  private Set<String> authors;
  private LocalDate publishedDate;

  public Book toBook() {
    return Book.builder()
        .title(this.title)
        .authors(this.authors)
        .publishedDate(this.publishedDate)
        .build();
  }
}
