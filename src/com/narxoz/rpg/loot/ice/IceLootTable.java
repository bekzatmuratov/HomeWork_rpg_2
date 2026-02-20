package com.narxoz.rpg.loot.ice;

import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Ice-themed loot table.
 */

public class IceLootTable implements LootTable {
    private final List<String> items;
    private final int goldDrop;
    private final int experienceDrop;

    public IceLootTable() {
        this.items = new ArrayList<>();
        items.add("Ice Gem");
        items.add("Frost Scale");
        items.add("Ice Rune");

        this.goldDrop = 450;
        this.experienceDrop = 1100;
    }

    private IceLootTable(List<String> items, int goldDrop, int experienceDrop) {
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
        return "Ice Loot: " + items +
                " | Gold: " + goldDrop +
                " | EXP: " + experienceDrop;
    }

    @Override
    public LootTable clone() {
        return new IceLootTable(
                new ArrayList<>(this.items),
                this.goldDrop,
                this.experienceDrop
        );
    }
}
