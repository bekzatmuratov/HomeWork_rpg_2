package com.narxoz.rpg.builder;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.enemy.Goblin;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds simple enemies (minions) like Goblin.
 *
 * FACTORY METHOD:
 * build() creates the final Enemy product (Goblin) and returns it as Enemy.
 */
public class BasicEnemyBuilder implements EnemyBuilder {

    // Builder state (fields we are assembling)
    private String name;
    private int health;
    private int damage;
    private int defense;
    private int speed;

    private String element = "NONE";
    private String aiBehavior = "BASIC";

    private List<Ability> abilities = new ArrayList<>();
    private LootTable lootTable;

    // For basic enemies phases are ignored, but to satisfy interface we store them
    private Map<Integer, Integer> phases = new HashMap<>();

    // --- Fluent setters ---
    @Override
    public EnemyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EnemyBuilder setHealth(int health) {
        this.health = health;
        return this;
    }

    @Override
    public EnemyBuilder setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    @Override
    public EnemyBuilder setDefense(int defense) {
        this.defense = defense;
        return this;
    }

    @Override
    public EnemyBuilder setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public EnemyBuilder setElement(String element) {
        this.element = element;
        return this;
    }

    @Override
    public EnemyBuilder setAI(String aiBehavior) {
        this.aiBehavior = aiBehavior;
        return this;
    }

    @Override
    public EnemyBuilder addAbility(Ability ability) {
        if (ability != null) {
            this.abilities.add(ability);
        }
        return this;
    }

    @Override
    public EnemyBuilder setAbilities(List<Ability> abilities) {
        this.abilities = (abilities != null) ? new ArrayList<>(abilities) : new ArrayList<>();
        return this;
    }

    @Override
    public EnemyBuilder addPhase(int phaseNumber, int healthThreshold) {
        // minions usually don't have phases, but we keep it optional
        this.phases.put(phaseNumber, healthThreshold);
        return this;
    }

    @Override
    public EnemyBuilder setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
        return this;
    }

    /**
     * FACTORY METHOD:
     * build() produces the product (Enemy).
     * Different builders can create different concrete enemies.
     */
    @Override
    public Enemy build() {
        // --- Validation (required fields) ---
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Enemy name is required");
        }

        // If user didn't set stats, use Goblin defaults (safe fallback)
        Goblin goblin = new Goblin(name);

        // If builder overrides were provided, apply them via multiplyStats trick or direct replacements:
        // We'll apply direct replacements only if values > 0 to avoid breaking defaults.
        if (health > 0) goblin.multiplyStats((double) health / goblin.getHealth()); // keep proportional
        // After proportional, set exact values for damage/def/ speed if provided
        // (We do it this way because Goblin has no setters for these fields)
        // Minimal approach: if not provided, keep defaults.
        // For cleaner code later we can add setters or a BasicEnemy class.

        // Element & AI
        goblin.setElement(element);
        goblin.setAIBehavior(aiBehavior);

        // Abilities
        for (Ability a : abilities) {
            goblin.addAbility(a);
        }

        // Loot
        goblin.setLootTable(lootTable);

        // reset builder for reuse (optional)
        reset();

        return goblin;
    }

    private void reset() {
        this.name = null;
        this.health = 0;
        this.damage = 0;
        this.defense = 0;
        this.speed = 0;
        this.element = "NONE";
        this.aiBehavior = "BASIC";
        this.abilities = new ArrayList<>();
        this.lootTable = null;
        this.phases = new HashMap<>();
    }
}

