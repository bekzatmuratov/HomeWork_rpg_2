package com.narxoz.rpg.combat.fire;
import com.narxoz.rpg.combat.Ability;

/**
 * Fire-themed defensive ability.
 *
 * Fire Shield surrounds the enemy with flames,
 * reducing incoming damage and burning attackers.
 *
 * Prototype Pattern:
 * Must be cloneable to support deep copying of enemies.
 */
public class FireShield implements Ability {

    private final String name = "Fire Shield";
    private final int damage = 0; // defensive ability, no direct damage
    private final String description =
            "Creates a flaming barrier that reduces incoming damage and burns attackers.";

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
        return new FireShield();
    }
}
