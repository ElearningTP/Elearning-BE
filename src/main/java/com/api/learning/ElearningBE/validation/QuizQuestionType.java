package com.api.learning.ElearningBE.validation;

import com.api.learning.ElearningBE.validation.impl.QuizQuestionTypeValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = QuizQuestionTypeValidation.class)
public @interface QuizQuestionType {
    boolean allowNull() default false;
    String message() default "Invalid quiz question type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
