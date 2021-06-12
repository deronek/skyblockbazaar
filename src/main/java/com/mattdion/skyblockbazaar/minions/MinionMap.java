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
     * MinionMap updates.
     */
    public static final long MINIONS_UPDATE_INTERVAL_MINUTES = 2;
    private final Map<MinionID, Minion> minionMap;
    private Instant lastUpdated;

    /**
     * Constructs the MinionMap and fills it with level 0 Minions.
     */
    public MinionMap() {
        this.minionMap = new HashMap<>();

        for (MinionID id : MinionID.values()) {
            minionMap.put(id, new Minion(id, 0));
        }
    }

    private void updateMinionMap(Map<MinionID, Integer> minionLevels) {
        for (Map.Entry<MinionID, Integer> entry : minionLevels.entrySet()) {
            Minion minion = minionMap.get(entry.getKey());
            minion.setLevel(entry.getValue());
        }
        lastUpdated = Instant.now();
    }

    /**
     * Request the update of the MinionMap. The map gets updated
     * if the map hasn't been updated yet or it's outdated by interval
     * set by MINIONS_UPDATE_INTERVAL_MINUTES constant.
     *
     * @param minionLevels Map of Minion levels
     * @see MinionMap#MINIONS_UPDATE_INTERVAL_MINUTES
     */
    public void requestUpdateMinionMap(Map<MinionID, Integer> minionLevels) {
        if (lastUpdated == null ||
                Duration.between(lastUpdated, Instant.now())
                        .compareTo(Duration.ofMinutes(MINIONS_UPDATE_INTERVAL_MINUTES)) > 0) {
            updateMinionMap(minionLevels);
        }
    }

    /**
     * Force the update of the MinionMap.
     *
     * @param minionLevels Map of Minion levels
     */
    public void forceUpdateMinionMap(Map<MinionID, Integer> minionLevels) {
        updateMinionMap(minionLevels);
    }


    /**
     * @return copy of minionMap Map
     */
    public Map<MinionID, Minion> getMinionMap() {
        return new HashMap<>(minionMap);
    }


    /**
     * @return time of last MinionMap update
     */
    public Instant getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return "MinionMap{" +
                "minionMap=" + minionMap +
                '}';
    }
}
