package com.api.learning.ElearningBE.validation.impl;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.validation.CourseState;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CourseStateValidation implements ConstraintValidator<CourseState,Integer> {
    private boolean allowNull;
    @Override
    public void initialize(CourseState constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer state, ConstraintValidatorContext constraintValidatorContext) {
        if (state == null && allowNull){
            return true;
        }
        if (!Objects.equals(state, ELearningConstant.COURSE_STATE_CREATED)  &&
            !Objects.equals(state, ELearningConstant.COURSE_STATE_STARTED)){
            return false;
        }
        return true;
    }
}
