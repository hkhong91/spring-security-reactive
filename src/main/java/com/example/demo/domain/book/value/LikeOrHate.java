package com.example.demo.domain.book.value;

import com.example.demo.infrastructure.exception.DomainException;
import com.example.demo.infrastructure.exception.DomainMessage;
import lombok.Getter;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;

@Getter
public enum LikeOrHate {
  LIKE,
  HATE,
  NONE;

  public Update getIncrementUpdate(LikeOrHate request) {
    Pair<Integer, Integer> pair = switch (this) {
      case LIKE -> switch (request) {
        case LIKE -> throw new DomainException(DomainMessage.SAME_VALUE);
        case HATE -> Pair.of(-1, 1);
        case NONE -> Pair.of(-1, 0);
      };
      case HATE -> switch (request) {
        case LIKE -> Pair.of(1, -1);
        case HATE -> throw new DomainException(DomainMessage.SAME_VALUE);
        case NONE -> Pair.of(0, -1);
      };
      case NONE -> switch (request) {
        case LIKE -> Pair.of(1, 0);
        case HATE -> Pair.of(0, 1);
        case NONE -> throw new DomainException(DomainMessage.SAME_VALUE);
      };
    };

    Update update = new Update();
    int likeCount = pair.getFirst();
    int hateCount = pair.getSecond();
    if (likeCount != 0) update.inc("likeCount", likeCount);
    if (hateCount != 0) update.inc("hateCount", hateCount);
    return update;
  }
}
