package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Goblin implements Enemy {

    private String name;
    private int health;
    private int damage;
    private int defense;
    private int speed;

    private String element = "NONE";
    private String aiBehavior = "BASIC";

    private List<Ability> abilities;
    private LootTable lootTable;

    public Goblin(String name) {
        this.name = name;
        this.health = 100;
        this.damage = 15;
        this.defense = 5;
        this.speed = 35;
        this.abilities = new ArrayList<>();
        this.lootTable = null;
    }

    // --- getters ---
    @Override public String getName() { return name; }
    @Override public int getHealth() { return health; }
    @Override public int getDamage() { return damage; }
    @Override public int getDefense() { return defense; }
    @Override public int getSpeed() { return speed; }

    @Override public String getElement() { return element; }
    @Override public String getAIBehavior() { return aiBehavior; }

    @Override public List<Ability> getAbilities() { return abilities; }
    @Override public LootTable getLootTable() { return lootTable; }

    @Override
    public Map<Integer, Integer> getPhases() {
        return new HashMap<>(); // goblin is not a boss
    }

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
        System.out.println(lootTable == null ? "Loot: (none)" : lootTable.getLootInfo());
        System.out.println();
    }

    // --- Prototype (временно простая версия; deep copy сделаем отдельным коммитом) ---
    @Override
    public Enemy clone() {
        Goblin copy = new Goblin(this.name);
        copy.health = this.health;
        copy.damage = this.damage;
        copy.defense = this.defense;
        copy.speed = this.speed;
        copy.element = this.element;
        copy.aiBehavior = this.aiBehavior;
        copy.abilities = new ArrayList<>(this.abilities);
        copy.lootTable = this.lootTable;
        return copy;
    }

    // --- Variant helpers ---
    @Override
    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    @Override
    public void setElement(String element) {
        this.element = element;
    }

    @Override
    public void multiplyStats(double multiplier) {
        this.health = (int) Math.round(this.health * multiplier);
        this.damage = (int) Math.round(this.damage * multiplier);
        this.defense = (int) Math.round(this.defense * multiplier);
        this.speed = (int) Math.round(this.speed * multiplier);
    }

    // Extra setters for Builder later (package-level ok, но пока оставим как есть)
    public void setAIBehavior(String aiBehavior) { this.aiBehavior = aiBehavior; }
    public void setLootTable(LootTable lootTable) { this.lootTable = lootTable; }
}

