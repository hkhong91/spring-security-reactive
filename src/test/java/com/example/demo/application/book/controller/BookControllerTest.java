package com.example.demo.application.book.controller;

import com.example.demo.application.AbstractControllerTest;
import com.example.demo.application.book.model.request.BookCategoryRequest;
import com.example.demo.application.book.model.request.BookRequest;
import com.example.demo.domain.book.value.CategoryCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
class BookControllerTest extends AbstractControllerTest {

  @Test
  void getBook() {
    client.get()
        .uri("/books/{bookId}", "61051fa985de2038eccc5393")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void readBook() {
    client.get()
        .uri("/books/{bookId}/read", "61051fa985de2038eccc5393")
        .header("Authorization", JWT)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void getBooks() {
    client.get()
        .uri("/books")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void readBooks() {
    client.get()
        .uri("/books/read")
        .header("Authorization", JWT)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void createBook() {
    client.post()
        .uri("/books")
        .header("Authorization", JWT)
        .bodyValue(new BookRequest(
            "Kotlin 200제",
            "프로그래밍 입문자의 시선으로 쓴 코틀린 문법서!",
            Set.of("엄민석"),
            LocalDate.of(2018, 5, 15),
            10000,
            10))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void updateBook() {
    client.patch()
        .uri("/books/{bookId}", "61051fa985de2038eccc5393")
        .header("Authorization", JWT)
        .bodyValue(new BookRequest(
            "자바 최적화",
            "가장 빠른 성능을 구현하는 검증된 10가지 기법",
            Set.of("벤저민 J. 에번스", "제임스 고프", "크리스 뉴랜드"),
            LocalDate.of(2019, 4, 29),
            20000,
            10))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void addBookCategories() {
    client.put()
        .uri("/books/{bookId}/categories", "61051fa985de2038eccc5393")
        .header("Authorization", JWT)
        .bodyValue(new BookCategoryRequest(Set.of(CategoryCode.CHILD, CategoryCode.COMIC)))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void deleteBook() {
    client.delete()
        .uri("/books/{bookId}", "611686c816be7d70d9b2f5f4")
        .header("Authorization", JWT)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  void aggregateHits() {
    client.put()
        .uri("/books/hits")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println);
  }
}