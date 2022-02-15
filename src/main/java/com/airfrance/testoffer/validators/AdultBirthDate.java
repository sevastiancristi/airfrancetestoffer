package com.airfrance.testoffer.validators;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = UserBirthDateValidator.class)
public @interface AdultBirthDate {
    String message() default "Registered user must be an adult!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
