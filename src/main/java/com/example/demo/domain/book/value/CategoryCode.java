package com.example.demo.domain.book.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryCode {

  CHILD("어린이"),
  IT("정보기술"),
  SCIENCE("과학"),
  COMIC("만화"),
  ;

  private final String categoryName;
}
