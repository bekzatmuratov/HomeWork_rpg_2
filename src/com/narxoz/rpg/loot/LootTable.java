package com.narxoz.rpg.loot;

import java.util.List;

/**
 * Interface for enemy loot/drop tables in the RPG system.
 *
 * Prototype Pattern Note:
 * Loot tables must be DEEP-COPYABLE. When you clone an enemy,
 * its loot table must also be cloned (independent object).
 */
public interface LootTable {

    /**
     * @return list of possible item drops
     */
    List<String> getItems();

    /**
     * @return gold dropped by the enemy
     */
    int getGoldDrop();

    /**
     * @return experience dropped by the enemy
     */
    int getExperienceDrop();

    /**
     * Convenience method for demo output.
     */
    default String getLootInfo() {
        return "Items=" + getItems() + ", Gold=" + getGoldDrop() + ", EXP=" + getExperienceDrop();
    }

    /**
     * PROTOTYPE: Must return a NEW independent LootTable instance.
     */
    LootTable clone();
}
