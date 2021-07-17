package com.example.demo.application.request;

import com.example.demo.domain.value.LikeOrHate;
import lombok.Getter;

@Getter
public class BookHitRequest {

  private LikeOrHate likeOrHate;
}
