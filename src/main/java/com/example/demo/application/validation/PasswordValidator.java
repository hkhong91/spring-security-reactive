package com.example.demo.application.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#&()–:;',?/*~$^+=<>]).{8,20}$", value);
  }
}
