package com.mattdion.skyblockbazaar.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ProductID {
    // skipping seeds production for Wheat
    WHEAT,
    ENCHANTED_BREAD,

    CARROT,
    ENCHANTED_CARROT,

    POTATO,
    ENCHANTED_POTATO,
    ENCHANTED_BAKED_POTATO,

    PUMPKIN,
    ENCHANTED_PUMPKIN,

    MELON,
    ENCHANTED_MELON,
    ENCHANTED_GLISTERING_MELON,
    ENCHANTED_MELON_BLOCK,

    RED_MUSHROOM,
    ENCHANTED_RED_MUSHROOM,

    BROWN_MUSHROOM,
    ENCHANTED_BROWN_MUSHROOM,

    HUGE_MUSHROOM_1,
    ENCHANTED_HUGE_MUSHROOM_1,

    HUGE_MUSHROOM_2,
    ENCHANTED_HUGE_MUSHROOM_2,

    COCOA_BEANS,
    ENCHANTED_COCOA_BEANS,

    CACTUS,
    ENCHANTED_CACTUS_GREEN,
    ENCHANTED_CACTUS,

    SUGAR_CANE,
    ENCHANTED_SUGAR,
    ENCHANTED_PAPER,
    ENCHANTED_SUGAR_CANE,

    NETHER_WART,
    ENCHANTED_NETHER_WART,

    RABBIT,
    ENCHANTED_RABBIT,

    CHICKEN,
    ENCHANTED_CHICKEN,
    ENCHANTED_CHICKEN_SUPER_EGG,

    COW,
    ENCHANTED_COW,

    MUTTON,
    ENCHANTED_MUTTON,
    ENCHANTED_COOKED_MUTTON,

    PORK,
    ENCHANTED_PORK,
    ENCHANTED_GRILLED_PORK,

    COAL,
    ENCHANTED_COAL,
    ENCHANTED_COAL_BLOCK,

    COBBLESTONE,
    ENCHANTED_COBBLESTONE,

    IRON_INGOT,
    ENCHANTED_IRON,
    ENCHANTED_IRON_BLOCK,

    GOLD_INGOT,
    ENCHANTED_GOLD,
    ENCHANTED_GOLD_BLOCK,

    DIAMOND,
    ENCHANTED_DIAMOND,
    ENCHANTED_DIAMOND_BLOCK,

    LAPIS_LAZULI,
    ENCHANTED_LAPIS_LAZULI,
    ENCHANTED_LAPIS_LAZULI_BLOCK,

    EMERALD,
    ENCHANTED_EMERALD,
    ENCHANTED_EMERALD_BLOCK,

    REDSTONE,
    ENCHANTED_REDSTONE,
    ENCHANTED_REDSTONE_BLOCK,

    QUARTZ,
    ENCHANTED_QUARTZ,
    ENCHANTED_QUARTZ_BLOCK,

    OBSIDIAN,
    ENCHANTED_OBSIDIAN,

    GLOWSTONE_DUST,
    ENCHANTED_GLOWSTONE_DUST,
    ENCHANTED_GLOWSTONE,

    GRAVEL,
    FLINT,
    ENCHANTED_FLINT,

    ICE,
    PACKED_ICE,
    ENCHANTED_ICE,
    ENCHANTED_PACKED_ICE,

    ENDER_STONE,
    ENCHANTED_END_STONE,

    SNOWBALL,
    SNOW_BLOCK,
    ENCHANTED_SNOW_BLOCK,

    CLAY,
    ENCHANTED_CLAY,

    SAND,
    ENCHANTED_SAND,

    ROTTEN_FLESH,
    ENCHANTED_ROTTEN_FLESH,

    BONE,
    ENCHANTED_BONE,

    SPIDER,
    ENCHANTED_SPIDER,

    CAVE_SPIDER,
    ENCHANTED_CAVE_SPIDER,

    GUNPOWDER,
    ENCHANTED_GUNPOWDER,

    BLAZE_ROD,
    ENCHANTED_BLAZE_POWDER,
    ENCHANTED_BLAZE_ROD,

    ENDER_PEARL,
    ENCHANTED_ENDER_PEARL,

    GHAST_TEAR,
    ENCHANTED_GHAST_TEAR,

    SLIMEBALL,
    ENCHANTED_SLIMEBALL,
    ENCHANTED_SLIME_BLOCK,

    MAGMA_CREAM,
    ENCHANTED_MAGMA_CREAM,

    OAK_WOOD,
    ENCHANTED_OAK_WOOD,

    SPRUCE_WOOD,
    ENCHANTED_SPRUCE_WOOD,

    BIRCH_WOOD,
    ENCHANTED_BIRCH_WOOD,

    DARK_OAK_WOOD,
    ENCHANTED_DARK_OAK_WOOD,

    ACACIA_WOOD,
    ENCHANTED_ACACIA_WOOD,

    JUNGLE_WOOD,
    ENCHANTED_JUNGLE_WOOD;

    private static final Logger log = LoggerFactory.getLogger(ProductID.class);

    /**
     * Static method which returns {@link ProductID} with value of parameter {@link String}.
     * The value is first searched in the values of {@link ProductID}, then, if not found,
     * searched in {@link ProductConstants#productIDTranslateMap}.
     *
     * @param name {@link String}  value of expected {@link ProductID}
     * @return {@link ProductID} with value of parameter {@link String}, or {@code null} if not found
     */
    public static ProductID getProductId(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return ProductConstants.productIDTranslateMap.get(name);
        }
    }
}
