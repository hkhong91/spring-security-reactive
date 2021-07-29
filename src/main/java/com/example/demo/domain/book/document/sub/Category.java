package com.example.demo.domain.book.document.sub;

import com.example.demo.domain.book.value.CategoryCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
public class Category {

  private CategoryCode code;

  private String name;

  public static Category of(CategoryCode code) {
    return Category.builder()
        .code(code)
        .name(code.getCategoryName())
        .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return this.code == ((Category) o).code;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.code);
  }
}
