package com.example.demo.application.router;

import com.example.demo.application.handler.BookHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BookRouter {

  @Bean
  public RouterFunction<ServerResponse> bookRoutes(final BookHandler bookHandler) {
    return RouterFunctions.route()
        .GET("/v1/books", bookHandler::getBooksV1)
        .GET("/v2/books", bookHandler::getBooksV2)
        .GET("/v3/books", bookHandler::getBooksV3)
        .GET("/books/{id}", bookHandler::getBook)
        .POST("/books", bookHandler::createBook)
        .PATCH("/v1/books/{id}", bookHandler::updateBookV1)
        .PATCH("/v2/books/{id}", bookHandler::updateBookV2)
        .PATCH("/v3/books/{id}", bookHandler::updateBookV3)
        .PATCH("/v4/books/{id}", bookHandler::updateBookV4)
        .DELETE("/v1/books/{id}", bookHandler::deleteBookV1)
        .DELETE("/v2/books/{id}", bookHandler::deleteBookV2)
        .build();
  }
}
