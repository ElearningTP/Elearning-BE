package com.api.learning.ElearningBE.validation;

import com.api.learning.ElearningBE.validation.impl.GroupKindValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = GroupKindValidation.class)
public @interface GroupKind {
    boolean allowNull() default false;
    String message() default "Invalid group kind";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
