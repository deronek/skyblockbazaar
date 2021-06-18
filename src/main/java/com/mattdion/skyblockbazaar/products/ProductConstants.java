package com.mattdion.skyblockbazaar.products;

import com.google.gson.reflect.TypeToken;
import com.mattdion.skyblockbazaar.Utils;
import com.mattdion.skyblockbazaar.minions.MinionConstants;
import com.mattdion.skyblockbazaar.minions.MinionID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductConstants {
    public static final Map<ProductID, Map<MinionID, Double>> productInputNeeded;
    public static final Map<String, ProductID> productIDTranslateMap;
    private static final Logger log = LoggerFactory.getLogger(MinionConstants.class);

    static {
        try {
            productInputNeeded = Utils.loadResourceToMap(
                    "json/ProductInputNeeded.json",
                    new TypeToken<HashMap<ProductID, HashMap<MinionID, Double>>>() {
                    }.getType());
            checkProductInputNeededMap();
            log.info("Initialized productInputNeeded Map");

            productIDTranslateMap = Utils.loadResourceToMap(
                    "json/ProductIDTranslateMap.json",
                    new TypeToken<HashMap<String, ProductID>>() {
                    }.getType());
            log.info("Initialized productIDTranslateMap Map");

        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static void checkProductInputNeededMap() {
        for (Map.Entry<ProductID, Map<MinionID, Double>> e : productInputNeeded.entrySet()) {
            if (e.getKey() == null)
                throw new IllegalStateException("productInputNeeded Map can't contain null ProductID keys");

            for (Map.Entry<MinionID, Double> r : e.getValue().entrySet()) {
                if (r.getKey() == null)
                    throw new IllegalStateException("productInputNeeded Map can't contain null MinionID keys");
            }
        }
    }
}
