package com.narxoz.rpg.combat.ice;
import com.narxoz.rpg.combat.Ability;

/**
 * Ice-themed damage ability.
 *
 * Frost Breath deals AoE ice damage and slows enemies (conceptually).
 * In this homework, we represent the effect via description text.
 *
 * Prototype Pattern:
 * Must be cloneable to support deep copying of enemies.
 */
public class FrostBreath implements Ability {

    private final String name = "Frost Breath";
    private final int damage = 95;
    private final String description =
            "Breathes freezing air, dealing AoE damage and slowing enemies.";

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

    @Override
    public Ability clone() {
        return new FrostBreath();
    }
}