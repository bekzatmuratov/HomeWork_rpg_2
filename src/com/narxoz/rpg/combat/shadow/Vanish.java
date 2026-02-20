package com.narxoz.rpg.combat.shadow;
import com.narxoz.rpg.combat.Ability;

/**
 * Shadow-themed evasive/stealth ability.
 *
 * Vanish helps the enemy evade attacks (conceptually) and reposition.
 * No direct damage in this simplified model.
 *
 * Prototype Pattern:
 * Must be cloneable to support deep copying of enemies.
 */
public class Vanish implements Ability {

    private final String name = "Vanish";
    private final int damage = 0; // utility / evasive ability
    private final String description =
            "Turns invisible briefly, increasing evasion and avoiding incoming attacks.";

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
        return new Vanish();
    }
}