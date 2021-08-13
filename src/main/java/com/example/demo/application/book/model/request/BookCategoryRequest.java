package com.example.demo.application.book.model.request;

import com.example.demo.domain.book.document.sub.Category;
import com.example.demo.domain.book.value.CategoryCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCategoryRequest {

  @NotNull
  @Size(min = 1)
  private Set<CategoryCode> categoryCodes;

  public Set<Category> toCategories() {
    return this.categoryCodes.stream()
        .map(Category::of)
        .collect(Collectors.toSet());
  }
}
