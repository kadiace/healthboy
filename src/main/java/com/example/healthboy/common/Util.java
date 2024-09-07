package com.example.healthboy.common;

import java.util.logging.Logger;

import org.springframework.core.env.Environment;

public class Util {

    private static final Logger logger = Logger.getLogger(Util.class.getName());

    // Private constructor to prevent instantiation
    private Util() {
    }

    public static boolean isLocal() {
        Environment environment = ApplicationContextProvider.getApplicationContext().getEnvironment();
        String activeProfile = environment.getProperty("spring.profiles.active");
        return "local".equals(activeProfile);
    }

    /**
     * Converts a string to an enum value. Returns null if the string does not match
     * any enum value.
     *
     * @param <T>       The enum type
     * @param enumClass The class of the enum
     * @param value     The string to convert
     * @return The corresponding enum value, or null if the string does not match
     *         any enum value
     */
    public static <T extends Enum<T>> T safeValueOf(Class<T> enumClass, String value) {
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.warning(e.getMessage() + "\nSSO Type Value: " + value);
            return null;
        }
    }
}
