package com.api.learning.ElearningBE.validation.impl;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.validation.AssignmentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class AssignmentTypeValidation implements ConstraintValidator<AssignmentType,Integer> {
    private boolean allowNull;
    @Override
    public void initialize(AssignmentType constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer assignmentType, ConstraintValidatorContext constraintValidatorContext) {
        if (assignmentType == null && allowNull){
            return true;
        }
        if (!Objects.equals(assignmentType, ELearningConstant.ASSIGNMENT_TYPE_FILE)
            && !Objects.equals(assignmentType, ELearningConstant.ASSIGNMENT_TYPE_TEXT)){
            return false;
        }
        return true;
    }
}
