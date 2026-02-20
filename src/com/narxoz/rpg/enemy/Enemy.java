package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.List;
import java.util.Map;

public interface Enemy {

    // --- Basic stats ---
    String getName();
    int getHealth();
    int getDamage();
    int getDefense();
    int getSpeed();

    // --- Theme / behavior ---
    String getElement();
    String getAIBehavior();

    // --- Components ---
    List<Ability> getAbilities();
    LootTable getLootTable();

    // --- Boss phases (for bosses; for minions can be empty map) ---
    Map<Integer, Integer> getPhases();

    // --- Demo output ---
    void displayInfo();

    // --- Prototype ---
    Enemy clone();

    // --- Variant helpers (for Prototype variants) ---
    void addAbility(Ability ability);
    void setElement(String element);
    void multiplyStats(double multiplier);
}
