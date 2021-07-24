package com.example.demo.application.book.model;

import com.example.demo.domain.book.value.LikeOrHate;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BookLikeOrHateRequest {

  @NotNull
  private LikeOrHate likeOrHate;
}
