package com.api.learning.ElearningBE.validation.impl;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.validation.AssignmentState;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class AssignmentStateValidation implements ConstraintValidator<AssignmentState,Integer> {
    private boolean allowNull;
    @Override
    public void initialize(AssignmentState constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer assignmentState, ConstraintValidatorContext constraintValidatorContext) {
        if (assignmentState == null && allowNull){
            return true;
        }
        if (!Objects.equals(assignmentState, ELearningConstant.ASSIGNMENT_STATE_CREATED)
            && !Objects.equals(assignmentState, ELearningConstant.ASSIGNMENT_STATE_STARTED)
            && !Objects.equals(assignmentState, ELearningConstant.ASSIGNMENT_STATE_EXPIRED)){
            return false;
        }
        return true;
    }
}
