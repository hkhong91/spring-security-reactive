package com.example.demo.application.book.controller;

import com.example.demo.domain.book.document.sub.Category;
import com.example.demo.domain.book.value.CategoryCode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Validated
@RequiredArgsConstructor
public class CategoryController {

  @GetMapping("/categories")
  public Flux<Category> getCategories() {
    return Flux.fromArray(CategoryCode.values())
        .map(Category::of);
  }
}
