package com.narxoz.rpg.combat.shadow;
import com.narxoz.rpg.combat.Ability;

/**
 * Shadow-themed single-target damage ability.
 *
 * Shadow Strike deals high damage and applies "blind" (conceptually).
 * We represent status effects via description for this homework.
 *
 * Prototype Pattern:
 * Must be cloneable to support deep copying of enemies.
 */
public class ShadowStrike implements Ability {

    private final String name = "Shadow Strike";
    private final int damage = 140;
    private final String description =
            "Strikes from the shadows, dealing high damage and blinding the target.";

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
        return new ShadowStrike();
    }
}
