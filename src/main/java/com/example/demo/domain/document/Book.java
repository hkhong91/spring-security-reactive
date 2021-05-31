package com.example.demo.domain.document;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
public class Book {

  @Id
  private final String id;

  @Indexed
  private String title;

  @Indexed
  private Set<String> authors;

  private int publishedYear;

  @CreatedDate
  private final LocalDateTime createdAt;
}
