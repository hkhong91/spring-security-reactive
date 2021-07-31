package com.example.demo.domain.book.repository;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

public interface BookRepositoryCustom {

  Mono<UpdateResult> upsertById(String bookId, Update update);
}
