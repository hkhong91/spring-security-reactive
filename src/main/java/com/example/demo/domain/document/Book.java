package com.example.demo.domain.document;

import com.example.demo.domain.value.LikeOrHate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
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

  private Map<String, LikeOrHate> likeOrHates;

  public void like(String userId) {
    LikeOrHate likeOrHate = this.likeOrHates.get(userId);
    if (Objects.nonNull(likeOrHate)) {
      switch (likeOrHate) {
        case LIKE:
          this.likeCount--;
          this.likeOrHates.remove(userId);
          break;
        case HATE:
          this.hateCount--;
          this.likeCount++;
          this.likeOrHates.put(userId, LikeOrHate.LIKE);
          break;
      }
    } else {
      this.likeCount++;
      this.likeOrHates.put(userId, LikeOrHate.LIKE);
    }
  }

  public void hate(String userId) {
    LikeOrHate likeOrHate = this.likeOrHates.get(userId);
    if (Objects.nonNull(likeOrHate)) {
      switch (likeOrHate) {
        case LIKE:
          this.likeCount--;
          this.hateCount++;
          this.likeOrHates.put(userId, LikeOrHate.HATE);
          break;
        case HATE:
          this.hateCount--;
          this.likeOrHates.remove(userId);
          break;
      }
    } else {
      this.hateCount++;
      this.likeOrHates.put(userId, LikeOrHate.HATE);
    }
  }
}
