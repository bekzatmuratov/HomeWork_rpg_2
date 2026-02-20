package com.narxoz.rpg.loot.fire;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Fire-themed loot table.
 *
 * Fire enemies drop fire-related items.
 * Prototype Pattern:
 * LootTable must be deep-copyable.
 */

public class FireLootTable implements LootTable {
    private final List<String> items;
    private final int goldDrop;
    private final int experienceDrop;

    public FireLootTable() {
        this.items = new ArrayList<>();
        items.add("Fire Gem");
        items.add("Dragon Scale");
        items.add("Flame Rune");

        this.goldDrop = 500;
        this.experienceDrop = 1200;
    }

    private FireLootTable(List<String> items, int goldDrop, int experienceDrop) {
        this.items = items;
        this.goldDrop = goldDrop;
        this.experienceDrop = experienceDrop;
    }

    @Override
    public List<String> getItems() {
        return new ArrayList<>(items); // defensive copy
    }

    @Override
    public int getGoldDrop() {
        return goldDrop;
    }

    @Override
    public int getExperienceDrop() {
        return experienceDrop;
    }

    @Override
    public String getLootInfo() {
        return "Fire Loot: " + items +
                " | Gold: " + goldDrop +
                " | EXP: " + experienceDrop;
    }

    @Override
    public LootTable clone() {
        return new FireLootTable(
                new ArrayList<>(this.items),
                this.goldDrop,
                this.experienceDrop
        );
    }
}
