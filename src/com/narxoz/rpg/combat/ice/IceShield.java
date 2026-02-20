package com.narxoz.rpg.combat.ice;
import com.narxoz.rpg.combat.Ability;

/**
 * Ice-themed defensive ability.
 *
 * Ice Shield protects the enemy with a layer of ice,
 * reducing damage and increasing survivability (conceptually).
 *
 * Prototype Pattern:
 * Must be cloneable to support deep copying of enemies.
 */
public class IceShield implements Ability {

    private final String name = "Ice Shield";
    private final int damage = 0; // defensive ability
    private final String description =
            "Forms an icy barrier that reduces incoming damage and hardens defense.";

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
        return new IceShield();
    }
}
