package com.narxoz.rpg.combat.fire;

import com.narxoz.rpg.combat.Ability;

/**
 * Fire-themed damage ability.
 *
 * Flame Breath deals area-of-effect fire damage
 * and represents a classic dragon fire attack.
 *
 * Prototype Pattern:
 * This ability MUST be cloneable so enemies can be deep-copied.
 */
public class FlameBreath implements Ability {

    private final String name = "Flame Breath";
    private final int damage = 120;
    private final String description =
            "Unleashes a cone of fire, dealing heavy AoE damage and burning enemies.";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Prototype pattern — deep copy of ability.
     */
    @Override
    public Ability clone() {
        return new FlameBreath();
    }
}