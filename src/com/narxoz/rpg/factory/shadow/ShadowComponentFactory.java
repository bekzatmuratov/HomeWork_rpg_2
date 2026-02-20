package com.narxoz.rpg.factory.shadow;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.combat.shadow.ShadowStrike;
import com.narxoz.rpg.combat.shadow.Vanish;
import com.narxoz.rpg.factory.EnemyComponentFactory;
import com.narxoz.rpg.loot.LootTable;
import com.narxoz.rpg.loot.shadow.ShadowLootTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Abstract Factory for Shadow-themed enemies.
 */


public class ShadowComponentFactory implements EnemyComponentFactory {
    @Override
    public List<Ability> createAbilities() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new ShadowStrike());
        abilities.add(new Vanish());
        return abilities;
    }

    @Override
    public LootTable createLootTable() {
        return new ShadowLootTable();
    }

    @Override
    public String createAIBehavior() {
        return "TACTICAL";
    }
}
