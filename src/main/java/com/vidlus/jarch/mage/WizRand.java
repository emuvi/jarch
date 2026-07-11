package com.vidlus.jarch.mage;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A highly performant, thread-safe utility class for generating random numbers, 
 * booleans, characters, strings, UUIDs, and securely shuffling collections.
 * Leverages java.util.concurrent.ThreadLocalRandom to prevent thread contention.
 */
public class WizRand {

    private WizRand() {
    }

    /**
     * Generates a random boolean value.
     *
     * @return true or false with approximately 50/50 probability
     */
    public static Boolean getBool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * Evaluates a random outcome based on custom odds.
     * Generates a random integer between min and max (exclusive), and returns true if the result is strictly less than 'odds'.
     *
     * @param min  the minimum bounds for the random roll (inclusive)
     * @param max  the maximum bounds for the random roll (exclusive)
     * @param odds the threshold that triggers a true outcome
     * @return true if the random roll is less than the odds, false otherwise
     */
    public static Boolean getBool(Integer min, Integer max, Integer odds) {
        if (min == null || max == null || odds == null) return false;
        return getInt(min, max) < odds;
    }

    /**
     * Generates a random single digit integer.
     *
     * @return a random integer between 0 and 9 (inclusive)
     */
    public static Integer getDigit() {
        return ThreadLocalRandom.current().nextInt(10);
    }

    /**
     * Generates a completely random 32-bit integer spanning the full integer spectrum.
     *
     * @return a random integer
     */
    public static Integer getInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    /**
     * Generates a random integer securely bounded by a maximum value.
     *
     * @param max the upper bound (exclusive)
     * @return a random integer between 0 and max-1, or -1 if the max is less than or equal to 0
     */
    public static Integer getInt(Integer max) {
        if (max == null || max <= 0) {
            return -1;
        } else {
            return ThreadLocalRandom.current().nextInt(max);
        }
    }

    /**
     * Generates a random integer strictly contained between a minimum and maximum bound.
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return a random integer between min and max-1, or -1 if the bounds are invalid
     */
    public static Integer getInt(Integer min, Integer max) {
        if (min == null || max == null || max <= min) {
            return -1;
        } else {
            return ThreadLocalRandom.current().nextInt(min, max);
        }
    }

    /**
     * Generates a completely random 64-bit integer.
     *
     * @return a random long value
     */
    public static Long getLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * Generates a random long securely bounded by a maximum value.
     *
     * @param max the upper bound (exclusive)
     * @return a random long between 0 and max-1, or -1L if the max is less than or equal to 0
     */
    public static Long getLong(Long max) {
        if (max == null || max <= 0) {
            return -1L;
        } else {
            return ThreadLocalRandom.current().nextLong(max);
        }
    }

    /**
     * Generates a random long strictly contained between a minimum and maximum bound.
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return a random long between min and max-1, or -1L if the bounds are invalid
     */
    public static Long getLong(Long min, Long max) {
        if (min == null || max == null || max <= min) {
            return -1L;
        } else {
            return ThreadLocalRandom.current().nextLong(min, max);
        }
    }

    /**
     * A constant string of upper-case standard English alphabet characters.
     */
    public static final String simpleUpperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * A constant string containing all upper and lower case letters, plus numbers 0 through 9.
     */
    public static final String alphaNumericChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Retrieves a single random uppercase alphabet character.
     *
     * @return a random character from 'A' to 'Z'
     */
    public static char getChar() {
        return simpleUpperChars.charAt(ThreadLocalRandom.current().nextInt(simpleUpperChars.length()));
    }

    /**
     * Generates a random string containing only uppercase alphabet characters.
     *
     * @param size the required length of the string
     * @return the generated string, or an empty string if the size is 0 or less
     */
    public static String getChars(int size) {
        if (size <= 0) return "";
        var result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            result.append(getChar());
        }
        return result.toString();
    }

    /**
     * Generates a highly randomized token string containing upper case, lower case, and numeric characters.
     * Excellent for generation secure tokens or identifiers.
     *
     * @param size the required length of the string
     * @return the generated alphanumeric string, or an empty string if size is 0 or less
     */
    public static String getAlphaNumericString(int size) {
        if (size <= 0) return "";
        var result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            result.append(alphaNumericChars.charAt(ThreadLocalRandom.current().nextInt(alphaNumericChars.length())));
        }
        return result.toString();
    }

    /**
     * Generates a random float value securely distributed between 0.0 and 1.0.
     *
     * @return a random float
     */
    public static Float getFloat() {
        return ThreadLocalRandom.current().nextFloat();
    }

    /**
     * Generates a random double precision value securely distributed between 0.0 and 1.0.
     *
     * @return a random double
     */
    public static Double getDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    /**
     * Generates a pseudorandom double value following a Gaussian (normal) distribution.
     * The mean is 0.0, and the standard deviation is 1.0.
     *
     * @return a normally distributed random double
     */
    public static Double getGaussian() {
        return ThreadLocalRandom.current().nextGaussian();
    }

    /**
     * Overwrites an existing byte array with totally random bytes.
     *
     * @param bytes the byte array to fill
     */
    public static void getBytes(byte[] bytes) {
        if (bytes != null) {
            ThreadLocalRandom.current().nextBytes(bytes);
        }
    }

    /**
     * Rapidly generates a standard, randomly generated Version 4 Universally Unique Identifier.
     *
     * @return a string representing a standard UUID (e.g., 550e8400-e29b-41d4-a716-446655440000)
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Extracts a single, randomly selected element from an array.
     *
     * @param <T>       the type of elements inside the array
     * @param fromArray the array to pick from
     * @return a randomly selected item, or null if the array is null or empty
     */
    public static <T> T getItem(T[] fromArray) {
        if (fromArray != null && fromArray.length > 0) {
            return fromArray[ThreadLocalRandom.current().nextInt(fromArray.length)];
        }
        return null;
    }

    /**
     * Extracts a single, randomly selected element from a Java List.
     *
     * @param <T>      the type of elements inside the list
     * @param fromList the list to pick from
     * @return a randomly selected item, or null if the list is null or empty
     */
    public static <T> T getItem(List<T> fromList) {
        if (fromList != null && !fromList.isEmpty()) {
            return fromList.get(ThreadLocalRandom.current().nextInt(fromList.size()));
        }
        return null;
    }

    /**
     * Randomly scrambles the order of the elements inside a Java List.
     * Execution happens in-place (the original list is mutated).
     *
     * @param <T>  the type of elements inside the list
     * @param list the list to be shuffled
     */
    public static <T> void shuffle(List<T> list) {
        if (list != null && !list.isEmpty()) {
            Collections.shuffle(list, ThreadLocalRandom.current());
        }
    }
}
