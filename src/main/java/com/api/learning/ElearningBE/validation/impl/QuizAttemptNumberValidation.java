package com.api.learning.ElearningBE.validation.impl;

import com.api.learning.ElearningBE.validation.QuizAttemptNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class QuizAttemptNumberValidation implements ConstraintValidator<QuizAttemptNumber,Integer> {
    private boolean allowNull;
    @Override
    public void initialize(QuizAttemptNumber constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer attemptNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (attemptNumber == null && allowNull){
            return true;
        }
        if (Objects.nonNull(attemptNumber) && attemptNumber < 0){
            return false;
        }
        return true;
    }
}
