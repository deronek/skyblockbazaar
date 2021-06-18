package com.mattdion.skyblockbazaar.minions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Minion {
    private static final int MINION_MIN_LEVEL = 0;
    private static final int MINION_MAX_LEVEL = 12;
    private static final double FUEL_MIN_VALUE = 0.0;
    private static final double FUEL_MAX_VALUE = 3.0;

    private static final Logger log = LoggerFactory.getLogger(Minion.class);
    private final MinionID id;
    private final double minionProductionPerTick;
    private int level;
    private double fuelBonus;

    // value hardcoded in JSON file
    private double baseTickTime;
    // ----------------------------

    private double totalProductionPerHour;

    public Minion(MinionID id, int level) {
        this.id = id;

        this.level = level;
        this.fuelBonus = 0.25;

        // if it's not in the JSON file, we assume minionProductionPerTick equals 1
        Double minionProductionPerTick = MinionConstants.minionProductionPerTick.get(id.name());
        this.minionProductionPerTick = Objects.requireNonNullElse(minionProductionPerTick, 1.0);

        if (level != 0) {
            updateMinionBaseTickTime();
            updateTotalProductionPerHour();
        }
    }

    private void updateMinionBaseTickTime() {
        if (isLevelOkay())
            baseTickTime = MinionConstants.minionBaseTickTime.get(id.name()).get(level - 1);
        else
            baseTickTime = 0;
    }

    private void updateTotalProductionPerHour() {
        if (isLevelOkay())
            totalProductionPerHour = minionProductionPerTick * 3600 / (baseTickTime / (1 + fuelBonus)) / 2;
        else
            totalProductionPerHour = 0;
    }

    public MinionID getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level > MINION_MAX_LEVEL || level < MINION_MIN_LEVEL)
            throw new IllegalArgumentException("Tried to set illegal Minion level value");
        this.level = level;

        if (level != 0) {
            updateMinionBaseTickTime();
            updateTotalProductionPerHour();
        }
    }

    public double getFuelBonus() {
        return fuelBonus;
    }

    public void setFuelBonus(double fuelBonus) {
        if (fuelBonus > FUEL_MAX_VALUE || fuelBonus < FUEL_MIN_VALUE)
            throw new IllegalArgumentException("Tried to set illegal Minion fuel bonus value");
        this.fuelBonus = fuelBonus;

        if (level != 0) {
            updateTotalProductionPerHour();
        }
    }

    public double getBaseTickTime() {
        return baseTickTime;
    }

    public double getMinionProductionPerTick() {
        return minionProductionPerTick;
    }

    public double getTotalProductionPerHour() {
        return totalProductionPerHour;
    }

    private boolean isLevelOkay() {
        return level != 0;
    }

    @Override
    public String toString() {
        return "Minion{" +
                "id=" + id +
                ", minionProductionPerTick=" + minionProductionPerTick +
                ", level=" + level +
                ", fuelBonus=" + fuelBonus +
                ", baseTickTime=" + baseTickTime +
                ", totalProductionPerHour=" + totalProductionPerHour +
                '}';
    }
}
