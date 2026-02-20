package com.narxoz.rpg.factory.ice;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.combat.ice.FrostBreath;
import com.narxoz.rpg.combat.ice.IceShield;
import com.narxoz.rpg.factory.EnemyComponentFactory;
import com.narxoz.rpg.loot.LootTable;
import com.narxoz.rpg.loot.ice.IceLootTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Abstract Factory for Ice-themed enemies.
 */

public class IceComponentFactory implements EnemyComponentFactory {
    @Override
    public List<Ability> createAbilities() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new FrostBreath());
        abilities.add(new IceShield());
        return abilities;
    }

    @Override
    public LootTable createLootTable() {
        return new IceLootTable();
    }

    @Override
    public String createAIBehavior() {
        return "DEFENSIVE";
    }
}
