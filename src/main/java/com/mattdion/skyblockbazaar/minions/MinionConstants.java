package com.mattdion.skyblockbazaar.minions;

import com.google.gson.reflect.TypeToken;
import com.mattdion.skyblockbazaar.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MinionConstants {
    public static final Map<String, Double> minionProductionPerTick;
    public static final Map<String, List<Double>> minionBaseTickTime;
    private static final Logger log = LoggerFactory.getLogger(MinionConstants.class);

    static {
        try {
            minionProductionPerTick = Utils.loadResourceToMap(
                    "json/MinionProductionPerTick.json",
                    new TypeToken<HashMap<String, Double>>() {
                    }.getType());
            log.info("Initialized minionProductionPerTick Map");

            minionBaseTickTime = Utils.loadResourceToMap(
                    "json/MinionBaseTickTime.json",
                    new TypeToken<HashMap<String, List<Double>>>() {
                    }.getType());
            log.info("Initialized minionBaseTickTime Map");

            minionBaseTickTime.forEach((k, v) -> {
                if (v.size() != 12) {
                    log.warn("Minion " + k + "is bad");
                }
            });
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
