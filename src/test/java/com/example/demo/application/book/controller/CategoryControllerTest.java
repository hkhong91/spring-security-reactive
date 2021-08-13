package com.example.demo.application.book.controller;

import com.example.demo.application.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryControllerTest extends AbstractControllerTest {

  @Test
  void getCategories() {
    client.get()
        .uri("/categories")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }
}