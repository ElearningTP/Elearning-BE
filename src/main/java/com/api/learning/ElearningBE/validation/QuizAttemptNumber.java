package com.api.learning.ElearningBE.validation;

import com.api.learning.ElearningBE.validation.impl.QuizAttemptNumberValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = QuizAttemptNumberValidation.class)
public @interface QuizAttemptNumber {
    boolean allowNull() default false;
    String message() default "Invalid quiz attempt number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
