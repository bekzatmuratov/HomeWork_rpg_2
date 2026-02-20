package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example complex boss enemy — THE REASON BUILDER PATTERN EXISTS.
 *
 * Telescoping constructor is intentionally here to show the pain.
 * Later, you will refactor construction through Builder.
 */
public class DragonBoss implements Enemy {

    // --- Basic Stats ---
    private String name;
    private int health;
    private int damage;
    private int defense;
    private int speed;

    // --- Elemental Theme ---
    private String element;

    // --- Abilities ---
    private List<Ability> abilities;

    // --- Boss Phases (health thresholds that trigger behavior changes) ---
    private Map<Integer, Integer> phases;

    // --- Loot ---
    private LootTable lootTable;

    // --- AI Behavior ---
    private String aiBehavior;

    // --- Special Properties ---
    private boolean canFly;
    private boolean hasBreathAttack;
    private int wingspan;

    /**
     * THE TELESCOPING CONSTRUCTOR FROM HELL.
     * (Later should be used only by Builder / or made package-private)
     */
    public DragonBoss(String name, int health, int damage, int defense,
                      int speed, String element,
                      List<Ability> abilities,
                      int phase1Threshold, int phase2Threshold, int phase3Threshold,
                      LootTable lootTable, String aiBehavior,
                      boolean canFly, boolean hasBreathAttack, int wingspan) {

        this.name = name;
        this.health = health;
        this.damage = damage;
        this.defense = defense;
        this.speed = speed;
        this.element = element;

        this.abilities = (abilities != null) ? abilities : new ArrayList<>();

        this.phases = new HashMap<>();
        this.phases.put(1, phase1Threshold);
        this.phases.put(2, phase2Threshold);
        this.phases.put(3, phase3Threshold);

        this.lootTable = lootTable;
        this.aiBehavior = aiBehavior;

        this.canFly = canFly;
        this.hasBreathAttack = hasBreathAttack;
        this.wingspan = wingspan;
    }

    // ============================================================
    // Enemy interface methods
    // ============================================================

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
        System.out.println("=== " + name + " (Dragon Boss) ===");
        System.out.println("Health: " + health + " | Damage: " + damage
                + " | Defense: " + defense + " | Speed: " + speed);
        System.out.println("Element: " + element);

        System.out.println("Abilities (" + abilities.size() + "):");
        for (Ability a : abilities) {
            System.out.println("  - " + a.getName() + " (" + a.getDamage() + "): " + a.getDescription());
        }

        System.out.println("Boss Phases: " + phases.size());
        for (Map.Entry<Integer, Integer> phase : phases.entrySet()) {
            System.out.println("  Phase " + phase.getKey()
                    + ": triggers at " + phase.getValue() + " HP");
        }

        System.out.println("AI Behavior: " + aiBehavior);
        System.out.println("Can Fly: " + canFly
                + " | Breath Attack: " + hasBreathAttack
                + " | Wingspan: " + wingspan);

        System.out.println(lootTable == null ? "Loot: (none)" : lootTable.getLootInfo());
        System.out.println();
    }

    /**
     * Prototype pattern — DEEP COPY.
     *
     * Deep copy required fields:
     * - abilities: new list + clone each ability
     * - phases: new map copy
     * - lootTable: clone
     */
    @Override
    public Enemy clone() {
        // Deep copy abilities
        List<Ability> abilitiesCopy = new ArrayList<>();
        for (Ability a : this.abilities) {
            abilitiesCopy.add(a.clone());
        }

        // Deep copy loot
        LootTable lootCopy = (this.lootTable == null) ? null : this.lootTable.clone();

        // Copy phases (Integer->Integer, safe to copy entries)
        int p1 = this.phases.getOrDefault(1, 0);
        int p2 = this.phases.getOrDefault(2, 0);
        int p3 = this.phases.getOrDefault(3, 0);

        // Create new DragonBoss
        DragonBoss copy = new DragonBoss(
                this.name,
                this.health,
                this.damage,
                this.defense,
                this.speed,
                this.element,
                abilitiesCopy,
                p1, p2, p3,
                lootCopy,
                this.aiBehavior,
                this.canFly,
                this.hasBreathAttack,
                this.wingspan
        );

        // If later you add more phases than 3, keep them too:
        copy.phases = new HashMap<>(this.phases);

        return copy;
    }

    // ============================================================
    // Variant helpers (for Prototype variants)
    // ============================================================

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

    // Optional: Builder will set these (можно оставить public пока, потом закроем)
    public void setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
    }

    public void setAIBehavior(String aiBehavior) {
        this.aiBehavior = aiBehavior;
    }
}
