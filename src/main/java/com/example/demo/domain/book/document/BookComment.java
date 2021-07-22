package com.example.demo.domain.book.document;

import com.example.demo.domain.book.document.sub.Author;
import com.example.demo.infrastructure.exception.DomainException;
import com.example.demo.infrastructure.exception.DomainMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "BookComment")
@Getter
@Setter
@Builder
public class BookComment {

  @Id
  private final String id;

  @Indexed
  private final String bookId;

  @Indexed
  @CreatedBy
  private final Author author;

  @CreatedDate
  private final LocalDateTime createdAt;

  @LastModifiedDate
  private final LocalDateTime updatedAt;

  private String content;

  public void verify(String bookId, String userId) {
    if (this.author.getId().equals(userId)) {
      throw new DomainException(DomainMessage.FORBIDDEN);
    }
    if (this.bookId.equals(bookId)) {
      throw new DomainException(DomainMessage.STRANGE_DATA);
    }
  }
}
