package com.mattdion.skyblockbazaar.minions;

import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MinionMap {

    /**
     * Value in minutes representing how much time to wait between
     * {@link MinionMap} updates.
     */
    public static final long MINIONS_UPDATE_INTERVAL_MINUTES = 2;
    private final Map<MinionID, Minion> minions;
    private Instant lastUpdated;

    /**
     * Constructs the {@link MinionMap} and fills it with level 0 {@link Minion} instances.
     */
    public MinionMap() {
        this.minions = new HashMap<>();

        for (MinionID id : MinionID.values()) {
            minions.put(id, new Minion(id, 0));
        }
    }

    private void updateMinionMap(Map<MinionID, Integer> minionLevels) {
        for (Map.Entry<MinionID, Integer> entry : minionLevels.entrySet()) {
            Minion minion = minions.get(entry.getKey());
            minion.setLevel(entry.getValue());
        }
        lastUpdated = Instant.now();
    }

    /**
     * Request the update of the {@link MinionMap}. The map gets updated
     * if the map hasn't been updated yet or it's outdated by interval
     * set by {@link #MINIONS_UPDATE_INTERVAL_MINUTES} constant.
     *
     * @param minionLevels {@link Map} of {@link Minion} levels
     */
    public void requestUpdateMinionMap(Map<MinionID, Integer> minionLevels) {
        if (lastUpdated == null ||
                Duration.between(lastUpdated, Instant.now())
                        .compareTo(Duration.ofMinutes(MINIONS_UPDATE_INTERVAL_MINUTES)) > 0) {
            updateMinionMap(minionLevels);
        }
    }

    /**
     * Force the update of the {@link MinionMap}.
     *
     * @param minionLevels {@link Map} of {@link Minion} levels
     */
    public void forceUpdateMinionMap(Map<MinionID, Integer> minionLevels) {
        updateMinionMap(minionLevels);
    }


    /**
     * {@return {@link Map} of {@link Minion} instances}
     */
    public Map<MinionID, Minion> getMinions() {
        return new HashMap<>(minions);
    }


    /**
     * {@return {@link Instant} of last {@link MinionMap} update}
     */
    public Instant getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return "MinionMap{" +
                "minions=" + minions +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
