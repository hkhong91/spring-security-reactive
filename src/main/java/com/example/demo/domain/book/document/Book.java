package com.example.demo.domain.book.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "Book")
@Getter
@Setter
@Builder
public class Book {

  @Id
  private final String id;

  @CreatedDate
  private final LocalDateTime createdAt;

  @LastModifiedDate
  private final LocalDateTime updatedAt;

  private String title;

  private String introduction;

  private Set<String> authors;

  private LocalDate publishedDate;

  private int likeCount;

  private int hateCount;
}
