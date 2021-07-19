package com.example.demo.domain.document;

import com.example.demo.domain.value.LikeOrHate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "BookRead")
@CompoundIndexes({
    @CompoundIndex(name = "bookId_userId", def = "{'bookId': 1, 'userId':1}")
})
@Getter
@Setter
@Builder
public class BookRead {

  @Id
  private final String id;

  private final String bookId;

  private final String userId;

  @CreatedDate
  private final LocalDateTime createdAt;

  @LastModifiedDate
  private final LocalDateTime updatedAt;

  private LikeOrHate likeOrHate;

  public static BookRead of(String bookId, String userId) {
    return of(bookId, userId, LikeOrHate.NONE);
  }

  public static BookRead of(String bookId, String userId, LikeOrHate likeOrHate) {
    return BookRead.builder()
        .bookId(bookId)
        .userId(userId)
        .likeOrHate(likeOrHate)
        .build();
  }
}
