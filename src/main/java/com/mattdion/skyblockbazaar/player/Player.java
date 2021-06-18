package com.mattdion.skyblockbazaar.player;

import com.mattdion.skyblockbazaar.Utils;
import com.mattdion.skyblockbazaar.minions.Minion;
import com.mattdion.skyblockbazaar.minions.MinionID;
import com.mattdion.skyblockbazaar.minions.MinionMap;
import com.mattdion.skyblockbazaar.products.Product;
import com.mattdion.skyblockbazaar.products.ProductMap;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Class representing a Player who has Minions and Products.
 */
public class Player {
    /**
     * Constant representing the number of minutes between {@link Player} {@link UUID} update.
     */
    public static final long UUID_UPDATE_INTERVAL_MINUTES = 60;
    private final String name;

    private final UUID id;
    private final Instant lastUpdated;
    private MinionMap minionMap;
    private ProductMap productMap;

    /**
     * Constructs a Player object with certain UUID and name.
     *
     * @param name {@link String} representing {@link Player} name
     * @param id   {@link String} representing {@link UUID} Version 4 without dashes.
     */
    public Player(String name, String id) {
        this.name = name;

        // parsing UUID without dashes
        String id_parsed = Utils.addDashes(id);
        this.id = UUID.fromString(id_parsed);

        lastUpdated = Instant.now();
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public MinionMap getMinionMap() {
        return minionMap;
    }

    public ProductMap getProductMap() {
        return productMap;
    }

    /**
     * Updates {@link Player} {@link MinionMap} using a {@link Map} of {@link Minion} levels.
     *
     * @param minionLevels {@link Map} of {@link Minion} levels
     */
    public void updateMinionMap(Map<MinionID, Integer> minionLevels) {
        if (minionMap == null) minionMap = new MinionMap();
        minionMap.requestUpdateMinionMap(minionLevels);
    }

    /**
     * Adds the {@link Product} to the {@link PlayerMap}.
     *
     * @param product {@link Product} to be added to the {@link PlayerMap}
     */
    public void addToProductMap(Product product) {
        if (productMap == null) productMap = new ProductMap();
        productMap.addToProductMap(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
