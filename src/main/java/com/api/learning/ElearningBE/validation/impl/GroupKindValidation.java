package com.api.learning.ElearningBE.validation.impl;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.validation.GroupKind;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GroupKindValidation implements ConstraintValidator<GroupKind,Integer> {
    private boolean allowNull;
    @Override
    public void initialize(GroupKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer groupKind, ConstraintValidatorContext constraintValidatorContext) {
        if (groupKind == null && allowNull){
            return true;
        }
        if (!Objects.equals(groupKind, ELearningConstant.GROUP_KIND_ADMIN)
                && !Objects.equals(groupKind, ELearningConstant.GROUP_KIND_TEACHER)
                && !Objects.equals(groupKind, ELearningConstant.GROUP_KIND_STUDENT)){
            return false;
        }
        return true;
    }
}
