package com.mattdion.skyblockbazaar.minions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MinionConstants {
    private static final Logger log = LoggerFactory.getLogger(MinionConstants.class);
    public static final Map<String, Double> minionProductionPerTick;
    public static final Map<String, List<Double>> minionBaseTickTime;

    static {
        try {
            minionProductionPerTick = loadResourceToMap(
                    "static/MinionProductionPerTick.json",
                    new TypeToken<HashMap<String, Double>>() {
                    }.getType());
            log.info("Initialized minionProductionPerTick Map");

            minionBaseTickTime = loadResourceToMap(
                    "static/MinionBaseTickTime.json",
                    new TypeToken<HashMap<String, List<Double>>>() {
                    }.getType());
            log.info("Initialized minionBaseTickTime Map");
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static <K, V> Map<K, V> loadResourceToMap(String filepath, Type type) throws IOException {
        Path path = new ClassPathResource(filepath).getFile().toPath();
        String json = Files.readString(path);

        Map<K, V> map = new Gson().fromJson(json, type);
        return Collections.unmodifiableMap(map);
    }
}
