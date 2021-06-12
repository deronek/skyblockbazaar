package com.mattdion.skyblockbazaar.player;

import com.mattdion.skyblockbazaar.minions.MinionID;
import com.mattdion.skyblockbazaar.minions.MinionMap;

import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Player {
    public static final long UUID_UPDATE_INTERVAL_MINUTES = 60;
    private final String name;

    private final UUID id;
    private final Instant lastUpdated;
    private MinionMap minionMap;

    public Player(String name, String id) {
        this.name = name;

        // parsing UUID without dashes
        String id_parsed = id.replaceAll(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                "$1-$2-$3-$4-$5");
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

    public void updateMinionMap(HashMap<MinionID, Integer> minionLevels) {
        if(minionMap == null) minionMap = new MinionMap();
        minionMap.requestUpdateMinionMap(minionLevels);
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
