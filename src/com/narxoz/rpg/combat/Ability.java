package com.narxoz.rpg.combat;

/**
 * Interface for all enemy abilities in the RPG system.
 *
 * Prototype Pattern Note:
 * Abilities must be DEEP-COPYABLE. When you clone an enemy,
 * its abilities must also be cloned (independent objects).
 */
public interface Ability {

    /**
     * @return ability name (e.g., "Flame Breath")
     */
    String getName();

    /**
     * @return base damage of the ability
     */
    int getDamage();

    /**
     * @return description of the effect (e.g., "AoE + burn")
     */
    String getDescription();

    /**
     * PROTOTYPE: Each ability must be clonable.
     * This should return a NEW independent Ability instance.
     */
    Ability clone();
}

