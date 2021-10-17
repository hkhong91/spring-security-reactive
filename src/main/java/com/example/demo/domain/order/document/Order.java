package com.example.demo.domain.order.document;

import com.example.demo.domain.order.document.sub.OrderedBook;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Order")
@Getter
@Setter
@Builder
public class Order {

  @Id
  private final String id;

  @CreatedDate
  private final LocalDateTime createdAt;

  @LastModifiedDate
  private final LocalDateTime updatedAt;

  private String userId;

  private List<OrderedBook> orderedBooks;
}
