package com.api.learning.ElearningBE.validation.impl;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.validation.QuizQuestionType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class QuizQuestionTypeValidation implements ConstraintValidator<QuizQuestionType,Integer> {
    private boolean allowNull;
    @Override
    public void initialize(QuizQuestionType constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer questionType, ConstraintValidatorContext constraintValidatorContext) {
        if (questionType == null && allowNull){
            return true;
        }
        if (!Objects.equals(questionType, ELearningConstant.QUIZ_QUESTION_TYPE_SINGLE_CHOICE)
                && !Objects.equals(questionType, ELearningConstant.QUIZ_QUESTION_TYPE_MULTI_CHOICE)){
            return false;
        }
        return true;
    }
}
