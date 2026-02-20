package com.narxoz.rpg.loot.shadow;

import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Shadow-themed loot table.
 */

public class ShadowLootTable implements LootTable{
    private final List<String> items;
    private final int goldDrop;
    private final int experienceDrop;

    public ShadowLootTable() {
        this.items = new ArrayList<>();
        items.add("Shadow Gem");
        items.add("Dark Essence");
        items.add("Shadow Rune");

        this.goldDrop = 550;
        this.experienceDrop = 1300;
    }

    private ShadowLootTable(List<String> items, int goldDrop, int experienceDrop) {
        this.items = items;
        this.goldDrop = goldDrop;
        this.experienceDrop = experienceDrop;
    }

    @Override
    public List<String> getItems() {
        return new ArrayList<>(items);
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
        return "Shadow Loot: " + items +
                " | Gold: " + goldDrop +
                " | EXP: " + experienceDrop;
    }

    @Override
    public LootTable clone() {
        return new ShadowLootTable(
                new ArrayList<>(this.items),
                this.goldDrop,
                this.experienceDrop
        );
    }
}
