package com.example.demo.application.book.model.request;

import com.example.demo.application.user.security.AuthUser;
import com.example.demo.domain.book.document.Book;
import com.example.demo.domain.book.document.sub.Aggregation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

  private String title;
  private String introduction;
  private Set<String> authors;
  private LocalDate publishedDate;

  public Book toBook(AuthUser authUser) {
    return Book.builder()
        .title(this.title)
        .introduction(this.introduction)
        .authors(this.authors)
        .publishedDate(this.publishedDate)
        .aggregation(new Aggregation())
        .creator(authUser.toCreator())
        .build();
  }
}
