package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Complex boss enemy — the reason Builder exists.
 *
 * Prototype:
 * clone() MUST be deep copy:
 *  - abilities: new list + clone each Ability
 *  - lootTable: clone
 *  - phases: new map copy
 *
 * Builder-friendly:
 * Ideally constructed via BossEnemyBuilder (not direct telescoping calls).
 */
public class DragonBoss implements Enemy {

    // --- Basic Stats ---
    private String name;
    private int health;
    private int damage;
    private int defense;
    private int speed;

    // --- Theme / Behavior ---
    private String element;      // "FIRE", "ICE", "SHADOW", "NONE"
    private String aiBehavior;   // "AGGRESSIVE", "DEFENSIVE", "TACTICAL"

    // --- Components ---
    private List<Ability> abilities;
    private Map<Integer, Integer> phases; // phase -> hp threshold
    private LootTable lootTable;

    // --- Special Properties ---
    private boolean canFly;
    private boolean hasBreathAttack;
    private int wingspan;

    /**
     * Legacy telescoping constructor.
     * You may keep it for now, but in practice call it from Builder only.
     *
     * TIP: You can change this to package-private (remove 'public')
     * so only builders in the same package can call it.
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

        this.element = (element == null || element.trim().isEmpty()) ? "NONE" : element.trim().toUpperCase();
        this.aiBehavior = (aiBehavior == null || aiBehavior.trim().isEmpty()) ? "AGGRESSIVE" : aiBehavior.trim().toUpperCase();

        this.abilities = new ArrayList<>();
        if (abilities != null) {
            for (Ability a : abilities) {
                this.abilities.add(a.clone());
            }
        }

        this.phases = new HashMap<>();
        // Add only if > 0 to avoid weird zero thresholds
        if (phase1Threshold > 0) this.phases.put(1, phase1Threshold);
        if (phase2Threshold > 0) this.phases.put(2, phase2Threshold);
        if (phase3Threshold > 0) this.phases.put(3, phase3Threshold);

        this.lootTable = (lootTable == null) ? null : lootTable.clone();

        this.canFly = canFly;
        this.hasBreathAttack = hasBreathAttack;
        this.wingspan = wingspan;
    }

    // ----------------------------------------------------------------------
    // Enemy getters
    // ----------------------------------------------------------------------

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
        // defensive copy
        return new ArrayList<>(abilities);
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    @Override
    public Map<Integer, Integer> getPhases() {
        // defensive copy
        return new HashMap<>(phases);
    }

    // ----------------------------------------------------------------------
    // Demo display
    // ----------------------------------------------------------------------

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
            System.out.println("  Phase " + phase.getKey() + ": triggers at " + phase.getValue() + " HP");
        }

        System.out.println("AI Behavior: " + aiBehavior);
        System.out.println("Can Fly: " + canFly
                + " | Breath Attack: " + hasBreathAttack
                + " | Wingspan: " + wingspan);

        if (lootTable != null) {
            System.out.println(lootTable.getLootInfo());
        } else {
            System.out.println("No loot table set.");
        }
    }

    // ----------------------------------------------------------------------
    // Prototype (DEEP COPY)
    // ----------------------------------------------------------------------

    @Override
    public Enemy clone() {
        // Deep copy abilities list
        List<Ability> abilityCopies = new ArrayList<>();
        for (Ability a : this.abilities) {
            abilityCopies.add(a.clone());
        }

        // Clone loot
        LootTable lootCopy = (this.lootTable == null) ? null : this.lootTable.clone();

        // Build via constructor (phases as 3 thresholds)
        // If you may have more than 3 phases, we will still deep-copy them after construction.
        int p1 = this.phases.getOrDefault(1, 0);
        int p2 = this.phases.getOrDefault(2, 0);
        int p3 = this.phases.getOrDefault(3, 0);

        DragonBoss copy = new DragonBoss(
                this.name,
                this.health,
                this.damage,
                this.defense,
                this.speed,
                this.element,
                abilityCopies,
                p1, p2, p3,
                lootCopy,
                this.aiBehavior,
                this.canFly,
                this.hasBreathAttack,
                this.wingspan
        );

        // If phases map contains extra phases (4+), preserve them too.
        copy.phases = new HashMap<>(this.phases);

        return copy;
    }

    // ----------------------------------------------------------------------
    // Variant helpers (for Prototype variants)
    // ----------------------------------------------------------------------

    @Override
    public void addAbility(Ability ability) {
        if (ability == null) return;
        this.abilities.add(ability.clone());
    }

    @Override
    public void setAbilities(List<Ability> abilities) {
        this.abilities = new ArrayList<>();
        if (abilities == null) return;
        for (Ability a : abilities) {
            this.abilities.add(a.clone());
        }
    }

    @Override
    public void setLootTable(LootTable lootTable) {
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

    /**
     * Convenience for bosses: add/override a phase threshold.
     */
    public void addPhase(int phaseNumber, int healthThreshold) {
        if (phaseNumber <= 0) return;
        if (healthThreshold <= 0) return;
        this.phases.put(phaseNumber, healthThreshold);
    }

    // Optional getters for special properties (if you want them in demo / UML)
    public boolean canFly() {
        return canFly;
    }

    public boolean hasBreathAttack() {
        return hasBreathAttack;
    }

    public int getWingspan() {
        return wingspan;
    }
}