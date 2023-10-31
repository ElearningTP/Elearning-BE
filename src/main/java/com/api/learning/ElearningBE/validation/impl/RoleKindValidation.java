package com.api.learning.ElearningBE.validation.impl;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.validation.RoleKind;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleKindValidation implements ConstraintValidator<RoleKind,Integer> {
    private boolean allowNull;
    @Override
    public void initialize(RoleKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer roleKind, ConstraintValidatorContext constraintValidatorContext) {
        if (roleKind == null && allowNull){
            return true;
        }
        if (!Objects.equals(roleKind, ELearningConstant.ROLE_KIND_ADMIN)
                && !Objects.equals(roleKind, ELearningConstant.ROLE_KIND_TEACHER)
                && !Objects.equals(roleKind, ELearningConstant.ROLE_KIND_STUDENT)){
            return false;
        }
        return true;
    }
}
