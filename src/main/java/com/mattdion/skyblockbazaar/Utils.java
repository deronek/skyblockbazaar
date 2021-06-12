package com.mattdion.skyblockbazaar;

/**
 * Utility class containing static helper functions.
 */
public class Utils {

    private Utils() {
        throw new AssertionError("Supress default constructor for noninstantiability");
    }

    /**
     * @param s {@link String} to be changed
     * @return {@link String} without dashes
     */
    public static String deleteDashes(String s) {
        return s.replace("-", "");
    }
}
