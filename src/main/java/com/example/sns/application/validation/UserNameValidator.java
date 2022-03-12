package com.example.sns.application.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<UserName, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.hasLength(value)) {
      int length = value.length();
      return length >= 2 && length <= 20;
    } else {
      return false;
    }
  }
}
