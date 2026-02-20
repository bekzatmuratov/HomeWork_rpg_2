package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple enemy: Skeleton.
 *
 * Purpose:
 * - Third base template for Prototype pattern (required 3+ templates).
 * - Similar complexity to Goblin, but a bit tankier and slower.
 *
 * Prototype:
 * - clone() MUST deep-copy abilities + loot table + phases map.
 */
public class Skeleton implements Enemy {

    // --- Core stats ---
    private String name;
    private int health;
    private int damage;
    private int defense;
    private int speed;

    // --- Theme / behavior ---
    private String element;      // e.g. "NONE", "SHADOW"
    private String aiBehavior;   // e.g. "TACTICAL", "DEFENSIVE"

    // --- Components ---
    private List<Ability> abilities;
    private LootTable lootTable;

    // --- Boss phases (optional; for Skeleton usually empty) ---
    private Map<Integer, Integer> phases;

    public Skeleton(String name) {
        this.name = name;

        // Default Skeleton stats: sturdier than goblin, slower
        this.health = 140;
        this.damage = 18;
        this.defense = 12;
        this.speed = 20;

        this.element = "NONE";
        this.aiBehavior = "TACTICAL";

        this.abilities = new ArrayList<>();
        this.phases = new HashMap<>();
        this.lootTable = null;
    }

    // -------------------------
    // Enemy interface methods
    // -------------------------
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public String getElement() {
        return element;
    }

    @Override
    public String getAIBehavior() {
        return aiBehavior;
    }

    @Override
    public List<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    @Override
    public Map<Integer, Integer> getPhases() {
        return phases;
    }

    @Override
    public void displayInfo() {
        System.out.println("=== " + name + " (Skeleton) ===");
        System.out.println("Health: " + health + " | Damage: " + damage
                + " | Defense: " + defense + " | Speed: " + speed);
        System.out.println("Element: " + element + " | AI: " + aiBehavior);

        System.out.println("Abilities: " + abilities.size());
        for (Ability a : abilities) {
            System.out.println("  - " + a.getName() + " (" + a.getDamage() + "): " + a.getDescription());
        }

        if (phases != null && !phases.isEmpty()) {
            System.out.println("Boss Phases: " + phases.size());
            for (Map.Entry<Integer, Integer> e : phases.entrySet()) {
                System.out.println("  Phase " + e.getKey() + ": triggers at " + e.getValue() + " HP");
            }
        }

        if (lootTable != null) {
            System.out.println(lootTable.getLootInfo());
        } else {
            System.out.println("Loot: (none)");
        }
    }

    // -------------------------
    // Prototype (DEEP COPY)
    // -------------------------
    @Override
    public Enemy clone() {
        // DEEP COPY: clone must copy abilities + loot independently (no shared references).
        Skeleton copy = new Skeleton(this.name);

        // primitives / strings
        copy.health = this.health;
        copy.damage = this.damage;
        copy.defense = this.defense;
        copy.speed = this.speed;
        copy.element = this.element;
        copy.aiBehavior = this.aiBehavior;

        // deep-copy abilities
        copy.abilities = new ArrayList<>();
        for (Ability a : this.abilities) {
            copy.abilities.add(a.clone());
        }

        // deep-copy loot table
        copy.lootTable = (this.lootTable == null) ? null : this.lootTable.clone();

        // copy phases map (even if empty)
        copy.phases = new HashMap<>();
        if (this.phases != null) {
            copy.phases.putAll(this.phases);
        }

        return copy;
    }

    // -------------------------
    // Variant helpers
    // -------------------------
    @Override
    public void addAbility(Ability ability) {
        if (ability == null) return;
        this.abilities.add(ability);
    }

    @Override
    public void setAbilities(List<Ability> abilities) {
        this.abilities = new ArrayList<>();
        if (abilities != null) {
            for (Ability a : abilities) {
                this.abilities.add(a);
            }
        }
    }

    @Override
    public void setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
    }

    @Override
    public void setElement(String element) {
        this.element = (element == null || element.isBlank()) ? "NONE" : element;
    }

    @Override
    public void setAIBehavior(String aiBehavior) {
        this.aiBehavior = (aiBehavior == null || aiBehavior.isBlank()) ? "TACTICAL" : aiBehavior;
    }

    @Override
    public void multiplyStats(double multiplier) {
        if (multiplier <= 0) return;
        this.health = (int) Math.round(this.health * multiplier);
        this.damage = (int) Math.round(this.damage * multiplier);
        this.defense = (int) Math.round(this.defense * multiplier);
        this.speed = (int) Math.round(this.speed * multiplier);
    }
}
