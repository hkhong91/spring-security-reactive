package com.example.demo.domain.book.service;

import com.example.demo.domain.book.model.BookHitAggregation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookHitDomainService {

  private final ReactiveRedisTemplate<String, Integer> numberTemplate;
  private final ReactiveValueOperations<String, Integer> numberOperations;
  private final BookAggregationDomainService bookAggregationDomainService;

  public Mono<Boolean> hit(String bookId, String ip) {
    String key = "hit:" + bookId + ":" + ip + ":" + LocalDate.now();
    return numberOperations.setIfAbsent(key, 1, Duration.ofDays(1L))
        .map(result -> {
          if (result) numberOperations.increment("hits:" + bookId).subscribe();
          return result;
        });
  }

  public Flux<BookHitAggregation> aggregate() {
    return numberTemplate.keys("hits:*")
        .flatMap(key -> numberOperations.getAndSet(key, 0)
            .flatMap(count -> {
              String bookId = key.substring("hits:".length());
              if (count == 0)
                return numberOperations.delete(key)
                    .map(bool -> BookHitAggregation.of(bookId));
              else
                return bookAggregationDomainService.hit(bookId, count)
                    .map(result -> BookHitAggregation.of(bookId, result));
            }));
  }
}
