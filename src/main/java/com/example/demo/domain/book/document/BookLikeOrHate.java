package com.example.demo.domain.book.document;

import com.example.demo.domain.book.value.LikeOrHate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "BookLikeOrHate")
@CompoundIndex(name = "bookId_userId", def = "{'bookId': 1, 'userId': 1}", unique = true)
@Getter
@Setter
@Builder
public class BookLikeOrHate {

  @Id
  private final String id;

  private final String bookId;

  private final String userId;

  @CreatedDate
  private final LocalDateTime createdAt;

  private LikeOrHate value;

  public static BookLikeOrHate of(String bookId, String userId, LikeOrHate value) {
    return BookLikeOrHate.builder()
        .bookId(bookId)
        .userId(userId)
        .value(value)
        .build();
  }

  public static BookLikeOrHate none(String bookId, String userId) {
    return of(bookId, userId, null);
  }

  public static BookLikeOrHate like(String bookId, String userId) {
    return of(bookId, userId, LikeOrHate.LIKE);
  }

  public static BookLikeOrHate hate(String bookId, String userId) {
    return of(bookId, userId, LikeOrHate.HATE);
  }

  public boolean liked() {
    return this.value == LikeOrHate.LIKE;
  }

  public boolean hated() {
    return this.value == LikeOrHate.HATE;
  }
}
