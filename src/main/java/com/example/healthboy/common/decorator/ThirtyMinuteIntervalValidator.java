package com.example.healthboy.common.decorator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.util.Calendar;

public class ThirtyMinuteIntervalValidator implements ConstraintValidator<ThirtyMinuteInterval, Timestamp> {

    @Override
    public void initialize(ThirtyMinuteInterval constraintAnnotation) {
    }

    @Override
    public boolean isValid(Timestamp value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);

        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int milliseconds = calendar.get(Calendar.MILLISECOND);

        return (minutes == 0 || minutes == 30) && seconds == 0 && milliseconds == 0;
    }
}
