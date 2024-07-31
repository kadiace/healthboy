package com.example.healthboy.common.decorator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ThirtyMinuteIntervalValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ThirtyMinuteInterval {

    String message() default "Time must be in 30-minute intervals";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}