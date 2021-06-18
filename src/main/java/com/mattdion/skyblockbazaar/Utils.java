package com.mattdion.skyblockbazaar;

import com.google.gson.Gson;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class containing static helper functions.
 */
public class Utils {

    private Utils() {
        throw new AssertionError("Supress default constructor for noninstantiability");
    }

    /**
     * Static method which deletes all dashes ("-") from {@link String}.
     *
     * @param s {@link String} to be changed
     * @return {@link String} without dashes
     */
    public static String deleteDashes(String s) {
        return s.replace("-", "");
    }


    /**
     * Static method which add dashes to input {@link String}, interpreting it as
     * Version 4 {@link UUID} in {@link String} form.
     *
     * @param id {@link String} to be changed
     * @return {@link String} representing Version 4 {@link UUID}
     */
    public static String addDashes(String id) {
        return id.replaceAll(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                "$1-$2-$3-$4-$5");
    }

    /**
     * Static method which loads resource from file at filepath to {@link Map} of certain {@link Type}
     *
     * @param filepath {@link String} of filepath of the resource, relative to {@code src/main/resources}
     * @param type     {@link Type} of {@link Map} to be created
     * @param <K>      key of the {@link Map} to be created
     * @param <V>      value of the {@link Map} to be created
     * @return {@link Map} created from loading the resource
     * @throws IOException if an I/O error occurs
     */
    public static <K, V> Map<K, V> loadResourceToMap(String filepath, Type type) throws IOException {
        Path path = new ClassPathResource(filepath).getFile().toPath();
        String json = Files.readString(path);

        Map<K, V> map = new Gson().fromJson(json, type);
        return Collections.unmodifiableMap(map);
    }
}
