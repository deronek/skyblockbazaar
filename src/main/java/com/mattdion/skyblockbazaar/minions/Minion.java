package com.mattdion.skyblockbazaar.minions;

import com.mattdion.skyblockbazaar.exceptions.IllegalMinionState;

public class Minion {
    private static final int MINION_MAX_LEVEL = 11;
    private final MinionID id;
    private int level;
    private double fuelBonus;

    // values hardcoded in JSON files
    private double baseTickTime;
    private final double minionProductionPerTick;
    // ---------------------------------------

    private double totalProductionPerHour;

    public Minion(MinionID id, int level) {
        this.id = id;

        this.level = level;
        this.fuelBonus = 0.5;

        // if it's not in the JSON file, we assume minionProductionPerTick equals 1
        Double minionProductionPerTick = MinionConstants.minionProductionPerTick.get(id.name());
        if (minionProductionPerTick == null) {
            this.minionProductionPerTick = 1;
        } else {
            this.minionProductionPerTick = minionProductionPerTick;
        }

        if(level != 0) {
            updateMinionBaseTickTime();
            updateTotalProductionPerHour();
        }

    }

    private void updateMinionBaseTickTime() {
        baseTickTime = MinionConstants.minionBaseTickTime.get(id.name()).get(level - 1);
    }

    private void updateTotalProductionPerHour() {
        totalProductionPerHour = minionProductionPerTick * 3600 / (baseTickTime / (1 + fuelBonus)) / 2;
    }

    public MinionID getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;

        if(level != 0) {
            updateMinionBaseTickTime();
            updateTotalProductionPerHour();
        }
    }

    public double getFuelBonus() {
        return fuelBonus;
    }

    public void setFuelBonus(double fuelBonus) {
        this.fuelBonus = fuelBonus;

        if(level != 0) {
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
        checkLevel();
        return totalProductionPerHour;
    }

    private void checkLevel() {
        if(level == 0) throw new IllegalMinionState("Minion level is zero");
    }

    @Override
    public String toString() {
        return "Minion{" +
                "id=" + id +
                ", baseTickTime=" + baseTickTime +
                ", level=" + level +
                ", fuelBonus=" + fuelBonus +
                ", minionProductionPerTick=" + minionProductionPerTick +
                ", totalProductionPerHour=" + totalProductionPerHour +
                '}';
    }
}
