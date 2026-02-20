package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.List;
import java.util.Map;

/**
 * Base interface for all enemies in the RPG system.
 *
 * KEY REQUIREMENT:
 * - Enemies must be cloneable (Prototype pattern).
 * - clone() MUST perform DEEP COPY (abilities list, loot table, phases map).
 */
public interface Enemy {

    // -------------------------
    // Core stats
    // -------------------------
    String getName();

    int getHealth();

    int getDamage();

    int getDefense();

    int getSpeed();

    // -------------------------
    // Theme / behavior
    // -------------------------
    String getElement();

    String getAIBehavior();

    // -------------------------
    // Components
    // -------------------------
    List<Ability> getAbilities();

    LootTable getLootTable();

    /**
     * Boss phases (optional).
     * For non-boss enemies can be empty map.
     */
    Map<Integer, Integer> getPhases();

    // -------------------------
    // Demo / printing
    // -------------------------
    void displayInfo();

    // -------------------------
    // Prototype
    // -------------------------
    Enemy clone();

    // -------------------------
    // Variant helpers (used after cloning)
    // -------------------------
    void addAbility(Ability ability);

    void setAbilities(List<Ability> abilities);

    void setLootTable(LootTable lootTable);

    void setElement(String element);

    void setAIBehavior(String aiBehavior);

    void multiplyStats(double multiplier);
}