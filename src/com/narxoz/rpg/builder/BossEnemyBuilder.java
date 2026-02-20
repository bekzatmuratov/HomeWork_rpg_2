package com.narxoz.rpg.builder;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.enemy.DragonBoss;
import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds complex boss enemies (e.g., DragonBoss).
 *
 * FACTORY METHOD:
 * build() creates and returns the final Enemy product.
 */
public class BossEnemyBuilder implements EnemyBuilder {

    // --- Builder state ---
    private String name;
    private int health;
    private int damage;
    private int defense;
    private int speed;

    private String element = "NONE";
    private String aiBehavior = "BOSS";

    private List<Ability> abilities = new ArrayList<>();
    private LootTable lootTable;

    // Boss phases: phase -> threshold
    private Map<Integer, Integer> phases = new HashMap<>();

    // Special boss properties (optional)
    private boolean canFly = true;
    private boolean hasBreathAttack = true;
    private int wingspan = 20;

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
        this.phases.put(phaseNumber, healthThreshold);
        return this;
    }

    @Override
    public EnemyBuilder setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
        return this;
    }

    // --- Optional boss-specific settings (not in EnemyBuilder interface) ---
    public BossEnemyBuilder setCanFly(boolean canFly) {
        this.canFly = canFly;
        return this;
    }

    public BossEnemyBuilder setHasBreathAttack(boolean hasBreathAttack) {
        this.hasBreathAttack = hasBreathAttack;
        return this;
    }

    public BossEnemyBuilder setWingspan(int wingspan) {
        this.wingspan = wingspan;
        return this;
    }

    /**
     * FACTORY METHOD:
     * build() creates the concrete boss Enemy (DragonBoss).
     */
    @Override
    public Enemy build() {
        // --- Validation (mandatory fields) ---
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Boss name is required");
        }
        if (health <= 0) {
            throw new IllegalStateException("Boss health must be positive");
        }
        if (damage < 0 || defense < 0 || speed < 0) {
            throw new IllegalStateException("Stats cannot be negative");
        }

        // Default phases if user didn't define any
        int p1 = phases.getOrDefault(1, health);
        int p2 = phases.getOrDefault(2, Math.max(1, (int) Math.round(health * 0.6)));
        int p3 = phases.getOrDefault(3, Math.max(1, (int) Math.round(health * 0.3)));

        DragonBoss boss = new DragonBoss(
                name,
                health,
                damage,
                defense,
                speed,
                element,
                new ArrayList<>(abilities), // list copy (abilities themselves are objects)
                p1, p2, p3,
                lootTable,
                aiBehavior,
                canFly,
                hasBreathAttack,
                wingspan
        );

        // Keep any extra phases beyond 1..3 (if added)
        boss.getPhases().putAll(phases);

        // reset builder for reuse (optional)
        reset();

        return boss;
    }

    private void reset() {
        this.name = null;
        this.health = 0;
        this.damage = 0;
        this.defense = 0;
        this.speed = 0;

        this.element = "NONE";
        this.aiBehavior = "BOSS";

        this.abilities = new ArrayList<>();
        this.lootTable = null;
        this.phases = new HashMap<>();

        this.canFly = true;
        this.hasBreathAttack = true;
        this.wingspan = 20;
    }
}
