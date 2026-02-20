package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic enemy implementation — Goblin.
 *
 * Prototype:
 * clone() MUST do deep copy:
 *  - abilities list: new list + clone each ability
 *  - lootTable: clone
 *  - phases map: new map (here usually empty, but still deep-copied)
 */
public class Goblin implements Enemy {

    private String name;
    private int health;
    private int damage;
    private int defense;
    private int speed;

    private String element;      // for elemental variants (can be "NONE")
    private String aiBehavior;   // e.g., "AGGRESSIVE", "DEFENSIVE", "TACTICAL"

    private List<Ability> abilities;
    private LootTable lootTable;

    // Goblins are not bosses, but Enemy interface requires phases map.
    // We'll keep it empty for Goblin.
    private Map<Integer, Integer> phases;

    public Goblin(String name) {
        this.name = name;

        // Default goblin stats: weak but fast
        this.health = 100;
        this.damage = 15;
        this.defense = 5;
        this.speed = 35;

        this.element = "NONE";
        this.aiBehavior = "AGGRESSIVE";

        this.abilities = new ArrayList<>();
        this.phases = new HashMap<>();
        this.lootTable = null;
    }

    // -------------------------
    // Enemy getters
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
        // чтобы снаружи не могли менять список напрямую
        return new ArrayList<>(abilities);
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    @Override
    public Map<Integer, Integer> getPhases() {
        // goblin phases usually empty, but still return copy
        return new HashMap<>(phases);
    }

    // -------------------------
    // Demo display
    // -------------------------

    @Override
    public void displayInfo() {
        System.out.println("=== " + name + " (Goblin) ===");
        System.out.println("Health: " + health + " | Damage: " + damage
                + " | Defense: " + defense + " | Speed: " + speed);
        System.out.println("Element: " + element + " | AI: " + aiBehavior);

        System.out.println("Abilities: " + abilities.size());
        for (Ability a : abilities) {
            System.out.println("  - " + a.getName() + " (" + a.getDamage() + "): " + a.getDescription());
        }

        if (lootTable != null) {
            System.out.println(lootTable.getLootInfo());
        } else {
            System.out.println("No loot table set.");
        }
    }

    // -------------------------
    // Prototype (DEEP COPY)
    // -------------------------

    @Override
    public Enemy clone() {
        Goblin copy = new Goblin(this.name);

        // primitive fields
        copy.health = this.health;
        copy.damage = this.damage;
        copy.defense = this.defense;
        copy.speed = this.speed;

        // theme fields
        copy.element = this.element;
        copy.aiBehavior = this.aiBehavior;

        // deep copy abilities
        copy.abilities = new ArrayList<>();
        for (Ability a : this.abilities) {
            copy.abilities.add(a.clone());
        }

        // deep copy loot table
        copy.lootTable = (this.lootTable == null) ? null : this.lootTable.clone();

        // deep copy phases map (usually empty)
        copy.phases = new HashMap<>(this.phases);

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
        if (abilities == null) return;
        for (Ability a : abilities) {
            // чтобы не шарить ссылки — кладём клоны
            this.abilities.add(a.clone());
        }
    }

    @Override
    public void setLootTable(LootTable lootTable) {
        // тоже без shared reference
        this.lootTable = (lootTable == null) ? null : lootTable.clone();
    }

    @Override
    public void setElement(String element) {
        this.element = (element == null || element.trim().isEmpty()) ? "NONE" : element.trim().toUpperCase();
    }

    @Override
    public void setAIBehavior(String aiBehavior) {
        this.aiBehavior = (aiBehavior == null || aiBehavior.trim().isEmpty()) ? "AGGRESSIVE" : aiBehavior.trim().toUpperCase();
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
