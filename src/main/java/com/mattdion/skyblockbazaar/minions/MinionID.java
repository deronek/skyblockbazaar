package com.mattdion.skyblockbazaar.minions;

public enum MinionID {
    // TODO: add commented out minions; they have multiple product drops from one minion
    WHEAT,
    CARROT,
    POTATO,
    PUMPKIN,
    MELON,
    //MUSHROOM,
    COCOA,
    CACTUS,
    SUGAR_CANE,
    //COW,
    PIG,
    //CHICKEN,
    //SHEEP,
    //RABBIT,
    NETHER_WARTS,
    //FISHING,
    FLOWER,

    COAL,
    COBBLESTONE,
    IRON,
    GOLD,
    DIAMOND,
    LAPIS,
    EMERALD,
    REDSTONE,
    QUARTZ,
    OBSIDIAN,
    GLOWSTONE,
    GRAVEL,
    ICE,
    SAND,
    ENDER_STONE,
    SNOW,
    CLAY,
    MITHRIL,

    ZOMBIE,
    SKELETON,
    CREEPER,
    SPIDER,
    CAVESPIDER,
    BLAZE,
    MAGMA_CUBE,
    ENDERMAN,
    GHAST,
    SLIME,

    REVENANT,
    TARANTULA,
    VOIDLING,

    OAK,
    SPRUCE,
    BIRCH,
    DARK_OAK,
    ACACIA,
    JUNGLE;

    public static MinionID getMinionId(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
