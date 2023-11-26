package com.api.learning.ElearningBE.validation;

import com.api.learning.ElearningBE.validation.impl.AssignmentStateValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AssignmentStateValidation.class)
public @interface AssignmentState {
    boolean allowNull() default false;
    String message() default "Invalid assignment state";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  {};
}
