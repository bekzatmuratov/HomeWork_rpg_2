package com.narxoz.rpg.factory.fire;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.combat.fire.FlameBreath;
import com.narxoz.rpg.combat.fire.FireShield;
import com.narxoz.rpg.factory.EnemyComponentFactory;
import com.narxoz.rpg.loot.LootTable;
import com.narxoz.rpg.loot.fire.FireLootTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Abstract Factory for Fire-themed enemies.
 *
 * Guarantees that all components belong to the FIRE theme.
 */

public class FireComponentFactory implements EnemyComponentFactory {
    @Override
    public List<Ability> createAbilities() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new FlameBreath());
        abilities.add(new FireShield());
        return abilities;
    }

    @Override
    public LootTable createLootTable() {
        return new FireLootTable();
    }

    @Override
    public String createAIBehavior() {
        return "AGGRESSIVE";
    }
}
