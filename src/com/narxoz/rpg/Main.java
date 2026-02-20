package com.narxoz.rpg;

import com.narxoz.rpg.builder.BasicEnemyBuilder;
import com.narxoz.rpg.builder.BossEnemyBuilder;
import com.narxoz.rpg.builder.EnemyDirector;
import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.factory.EnemyComponentFactory;
import com.narxoz.rpg.factory.fire.FireComponentFactory;
import com.narxoz.rpg.factory.ice.IceComponentFactory;
import com.narxoz.rpg.factory.shadow.ShadowComponentFactory;
import com.narxoz.rpg.loot.LootTable;

import java.util.List;

/**
 * Main demonstration class for the RPG Enemy System.
 *
 * Shows:
 * 1) Abstract Factory — themed components
 * 2) Builder — complex enemy construction
 * 3) Factory Method — build() creates Enemy product
 * 4) Director — delegates creation via build()
 *
 * Prototype will be added later (registry + deep copy demo).
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== RPG Enemy System - Creational Patterns Capstone ===\n");

        // ============================================================
        // PART 1: ABSTRACT FACTORY PATTERN
        // ============================================================
        System.out.println("============================================");
        System.out.println("PART 1: ABSTRACT FACTORY - Themed Components");
        System.out.println("============================================\n");

        EnemyComponentFactory fireFactory = new FireComponentFactory();
        EnemyComponentFactory iceFactory = new IceComponentFactory();
        EnemyComponentFactory shadowFactory = new ShadowComponentFactory();

        createThemeDemo(fireFactory, "FIRE");
        createThemeDemo(iceFactory, "ICE");
        createThemeDemo(shadowFactory, "SHADOW");

        // ============================================================
        // PART 2: BUILDER PATTERN (with Factory Method inside build())
        // ============================================================
        System.out.println("============================================");
        System.out.println("PART 2: BUILDER - Complex Enemy Construction");
        System.out.println("============================================\n");

        // FACTORY METHOD: build() creates the Enemy product (DragonBoss) via BossEnemyBuilder
        Enemy ancientFireDragon = new BossEnemyBuilder()
                .setName("Ancient Fire Dragon")
                .setHealth(50000)
                .setDamage(550)
                .setDefense(260)
                .setSpeed(55)
                .setElement("FIRE")
                .setAI(fireFactory.createAIBehavior())
                .setAbilities(fireFactory.createAbilities())
                .setLootTable(fireFactory.createLootTable())
                .addPhase(1, 50000)
                .addPhase(2, 30000)
                .addPhase(3, 15000)
                .build(); // <-- FACTORY METHOD

        System.out.println("[Builder] Created boss using fluent API + themed components:");
        ancientFireDragon.displayInfo();

        // Build a simple enemy using BasicEnemyBuilder
        Enemy iceMinion = new BasicEnemyBuilder()
                .setName("Ice Minion")
                .setAI(iceFactory.createAIBehavior())
                .setAbilities(iceFactory.createAbilities())
                .setLootTable(iceFactory.createLootTable())
                .build(); // <-- FACTORY METHOD

        System.out.println("[Builder] Created simple enemy using BasicEnemyBuilder:");
        iceMinion.displayInfo();

        // ============================================================
        // PART 2.5: DIRECTOR (delegates Factory Method build())
        // ============================================================
        System.out.println("============================================");
        System.out.println("PART 2.5: DIRECTOR - Preset Enemy Recipes");
        System.out.println("============================================\n");

        // Director with BossEnemyBuilder -> produces bosses
        EnemyDirector bossDirector = new EnemyDirector(new BossEnemyBuilder());

        // Director delegates creation via builder.build()
        Enemy shadowRaidBoss = bossDirector.createRaidBoss(shadowFactory);
        System.out.println("[Director] Created Raid Boss using Shadow components:");
        shadowRaidBoss.displayInfo();

        Enemy fireMiniBoss = bossDirector.createMiniBoss(fireFactory);
        System.out.println("[Director] Created Mini Boss using Fire components:");
        fireMiniBoss.displayInfo();

        // Director with BasicEnemyBuilder -> produces minions
        EnemyDirector minionDirector = new EnemyDirector(new BasicEnemyBuilder());
        Enemy basicElite = minionDirector.createElite(iceFactory);
        System.out.println("[Director] Created Elite minion using Ice components:");
        basicElite.displayInfo();

        // ============================================================
        // SUMMARY (so teacher sees what patterns you already showed)
        // ============================================================
        System.out.println("============================================");
        System.out.println("PATTERN SUMMARY (so far)");
        System.out.println("============================================");
        System.out.println("Abstract Factory: Fire/Ice/Shadow factories produce matching abilities + loot + AI");
        System.out.println("Builder: BossEnemyBuilder and BasicEnemyBuilder assemble enemies step-by-step");
        System.out.println("Factory Method: build() creates the Enemy product (called directly + via Director)");
        System.out.println("Prototype: (next step) registry + deep copy cloning\n");

        System.out.println("=== Demo Complete (Builder + Director) ===");
    }

    private static void createThemeDemo(EnemyComponentFactory factory, String themeName) {
        List<Ability> abilities = factory.createAbilities();
        LootTable loot = factory.createLootTable();
        String ai = factory.createAIBehavior();

        System.out.println("[" + themeName + " FACTORY]");
        System.out.println("AI Behavior: " + ai);

        System.out.println("Abilities:");
        for (Ability a : abilities) {
            System.out.println("  - " + a.getName() + " (" + a.getDamage() + "): " + a.getDescription());
        }

        System.out.println("Loot:");
        System.out.println("  " + loot.getLootInfo());
        System.out.println();
    }
}
