package com.narxoz.rpg;

import com.narxoz.rpg.builder.BasicEnemyBuilder;
import com.narxoz.rpg.builder.BossEnemyBuilder;
import com.narxoz.rpg.builder.EnemyDirector;
import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.combat.fire.FlameBreath;
import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.factory.EnemyComponentFactory;
import com.narxoz.rpg.factory.fire.FireComponentFactory;
import com.narxoz.rpg.factory.ice.IceComponentFactory;
import com.narxoz.rpg.factory.shadow.ShadowComponentFactory;
import com.narxoz.rpg.loot.LootTable;
import com.narxoz.rpg.prototype.EnemyRegistry;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== RPG Enemy System - Creational Patterns Capstone ===\n");

        // ============================================================
        // PART 1: ABSTRACT FACTORY
        // ============================================================
        System.out.println("============================================");
        System.out.println("PART 1: ABSTRACT FACTORY - Themed Components");
        System.out.println("============================================\n");

        EnemyComponentFactory fireFactory = new FireComponentFactory();
        EnemyComponentFactory iceFactory = new IceComponentFactory();
        EnemyComponentFactory shadowFactory = new ShadowComponentFactory();

        showFactory("FIRE FACTORY", fireFactory);
        showFactory("ICE FACTORY", iceFactory);
        showFactory("SHADOW FACTORY", shadowFactory);

        // ============================================================
        // PART 2: BUILDER (+ Factory Method inside build())
        // ============================================================
        System.out.println("============================================");
        System.out.println("PART 2: BUILDER - Complex Enemy Construction");
        System.out.println("============================================\n");

        // IMPORTANT: use only builder methods from assignment:
        // setName, setHealth, setDamage, setDefense, setSpeed, setElement,
        // setAI, setAbilities, setLootTable, addPhase, build
        Enemy dragon = new BossEnemyBuilder()
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
                .build(); // FACTORY METHOD: product created here

        System.out.println("[Builder] Created boss using fluent API + themed components:");
        dragon.displayInfo();
        System.out.println();

        Enemy baseGoblin = new BasicEnemyBuilder()
                .setName("Goblin Template")
                .setElement("NONE")
                .setAI(iceFactory.createAIBehavior())
                .setAbilities(iceFactory.createAbilities())
                .setLootTable(iceFactory.createLootTable())
                .build();

        System.out.println("[Builder] Created base enemy template using BasicEnemyBuilder:");
        baseGoblin.displayInfo();
        System.out.println();

        // Director recipes
        System.out.println("============================================");
        System.out.println("PART 2.5: DIRECTOR - Preset Enemy Recipes");
        System.out.println("============================================\n");

        EnemyDirector bossDirector = new EnemyDirector(new BossEnemyBuilder());

        Enemy raidBoss = bossDirector.createRaidBoss(shadowFactory);
        System.out.println("[Director] Created Raid Boss using Shadow components:");
        raidBoss.displayInfo();
        System.out.println();

        Enemy miniBoss = bossDirector.createMiniBoss(fireFactory);
        System.out.println("[Director] Created Mini Boss using Fire components:");
        miniBoss.displayInfo();
        System.out.println();

        EnemyDirector basicDirector = new EnemyDirector(new BasicEnemyBuilder());

        Enemy eliteMinion = basicDirector.createElite(iceFactory);
        System.out.println("[Director] Created Elite minion using Ice components:");
        eliteMinion.displayInfo();
        System.out.println();

        // ============================================================
        // PART 3: PROTOTYPE (Registry + Deep Copy proof)
        // ============================================================
        System.out.println("============================================");
        System.out.println("PART 3: PROTOTYPE - Registry + Variants + Deep Copy Proof");
        System.out.println("============================================\n");

        EnemyRegistry registry = new EnemyRegistry();

        // Register templates (IMPORTANT: store templates, registry returns clones)
        registry.registerTemplate("goblin", baseGoblin);
        registry.registerTemplate("dragon", dragon);

        System.out.println("[Registry] Templates registered: " + registry.listTemplates());
        System.out.println();

        // ---- Difficulty variants from goblin template ----
        Enemy goblinElite = registry.createFromTemplate("goblin");
        goblinElite.multiplyStats(2.0);
        goblinElite.setAIBehavior("DEFENSIVE");

        Enemy goblinChampion = registry.createFromTemplate("goblin");
        goblinChampion.multiplyStats(5.0);
        goblinChampion.setAIBehavior("TACTICAL");
        goblinChampion.addAbility(new FlameBreath()); // extra ability

        System.out.println("[Prototype] Variants from 'goblin' template:");
        System.out.println("-> Elite Goblin (2x)");
        goblinElite.displayInfo();
        System.out.println();

        System.out.println("-> Champion Goblin (5x + extra ability)");
        goblinChampion.displayInfo();
        System.out.println();

        // ---- Elemental variants from dragon template ----
        Enemy fireDragon = registry.createFromTemplate("dragon");
        fireDragon.setElement("FIRE");
        fireDragon.setAIBehavior(fireFactory.createAIBehavior());
        fireDragon.setAbilities(fireFactory.createAbilities());
        fireDragon.setLootTable(fireFactory.createLootTable());

        Enemy iceDragon = registry.createFromTemplate("dragon");
        iceDragon.setElement("ICE");
        iceDragon.setAIBehavior(iceFactory.createAIBehavior());
        iceDragon.setAbilities(iceFactory.createAbilities());
        iceDragon.setLootTable(iceFactory.createLootTable());

        System.out.println("[Prototype] Elemental variants from 'dragon' template:");
        System.out.println("-> Fire Dragon");
        fireDragon.displayInfo();
        System.out.println();

        System.out.println("-> Ice Dragon");
        iceDragon.displayInfo();
        System.out.println();

        // ---- DEEP COPY PROOF ----
        System.out.println("============================================");
        System.out.println("DEEP COPY PROOF (modify clone -> template unchanged)");
        System.out.println("============================================\n");

        Enemy dragonTemplate = registry.viewTemplates().get("dragon");
        Enemy dragonClone = registry.createFromTemplate("dragon");

        System.out.println("[Before]");
        System.out.println("Template abilities count: " + dragonTemplate.getAbilities().size());
        System.out.println("Clone abilities count:    " + dragonClone.getAbilities().size());

        // Modify clone
        dragonClone.addAbility(new FlameBreath());

        System.out.println("\n[After modifying CLONE]");
        System.out.println("Template abilities count: " + dragonTemplate.getAbilities().size());
        System.out.println("Clone abilities count:    " + dragonClone.getAbilities().size());

        System.out.println("\nTemplate (must be unchanged):");
        dragonTemplate.displayInfo();

        System.out.println("\nClone (must be changed):");
        dragonClone.displayInfo();

        // ============================================================
        // SUMMARY
        // ============================================================
        System.out.println("\n============================================");
        System.out.println("PATTERN SUMMARY");
        System.out.println("============================================");
        System.out.println("Abstract Factory: Fire/Ice/Shadow factories produce matching abilities + loot + AI");
        System.out.println("Builder: BossEnemyBuilder and BasicEnemyBuilder assemble enemies step-by-step");
        System.out.println("Factory Method: build() creates the Enemy product (called directly + via Director)");
        System.out.println("Prototype: EnemyRegistry clones templates (deep copy) to create variants");

        System.out.println("\n=== Demo Complete ===");
    }

    private static void showFactory(String title, EnemyComponentFactory factory) {
        System.out.println("[" + title + "]");
        String ai = factory.createAIBehavior();
        List<Ability> abilities = factory.createAbilities();
        LootTable loot = factory.createLootTable();

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