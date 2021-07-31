package com.example.demo.domain.book.document;

import com.example.demo.domain.book.document.sub.Aggregation;
import com.example.demo.domain.book.document.sub.Category;
import com.example.demo.domain.book.document.sub.Creator;
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

  public static final String ID = "id";

  @Id
  private final String id;

  @CreatedDate
  private final LocalDateTime createdAt;

  @LastModifiedDate
  private final LocalDateTime updatedAt;

  private Set<Category> categories;

  private String title;

  private String introduction;

  private Set<String> authors;

  private Creator creator;

  private LocalDate publishedDate;

  private Aggregation aggregation;
}
