package com.narxoz.rpg.builder;

import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.factory.EnemyComponentFactory;

/**
 * Director = набор готовых "рецептов" создания врагов.
 *
 * Director НЕ создает Enemy напрямую.
 * Он настраивает Builder шагами и затем вызывает build().
 *
 * FACTORY METHOD DELEGATION:
 * Director вызывает builder.build() полиморфно, не зная,
 * какой конкретный Enemy будет создан (Goblin или DragonBoss).
 */
public class EnemyDirector {

    private final EnemyBuilder builder;

    public EnemyDirector(EnemyBuilder builder) {
        this.builder = builder;
    }

    /**
     * Minion = слабый враг (обычно без фаз, с минимумом статов).
     */
    public Enemy createMinion(EnemyComponentFactory factory) {
        // FACTORY METHOD: builder.build() creates the Enemy product
        return builder
                .setName("Minion")
                .setHealth(60)
                .setDamage(8)
                .setDefense(3)
                .setSpeed(30)
                .setElement("NONE")
                .setAI(factory.createAIBehavior())
                .setAbilities(factory.createAbilities())
                .setLootTable(factory.createLootTable())
                .build();
    }

    /**
     * Elite = усиленный обычный враг (средняя сложность).
     */
    public Enemy createElite(EnemyComponentFactory factory) {
        // FACTORY METHOD: builder.build() creates the Enemy product
        return builder
                .setName("Elite")
                .setHealth(200)
                .setDamage(35)
                .setDefense(20)
                .setSpeed(25)
                .setElement("NONE")
                .setAI(factory.createAIBehavior())
                .setAbilities(factory.createAbilities())
                .setLootTable(factory.createLootTable())
                .build();
    }

    /**
     * MiniBoss = мини-босс (уже может иметь фазы).
     */
    public Enemy createMiniBoss(EnemyComponentFactory factory) {
        // FACTORY METHOD: builder.build() creates the Enemy product
        return builder
                .setName("Mini Boss")
                .setHealth(5000)
                .setDamage(160)
                .setDefense(80)
                .setSpeed(40)
                .setElement("NONE")
                .setAI(factory.createAIBehavior())
                .setAbilities(factory.createAbilities())
                .setLootTable(factory.createLootTable())
                .addPhase(1, 5000)
                .addPhase(2, 3000)
                .addPhase(3, 1500)
                .build();
    }

    /**
     * RaidBoss = самый сложный босс.
     */
    public Enemy createRaidBoss(EnemyComponentFactory factory) {
        // FACTORY METHOD: builder.build() creates the Enemy product
        return builder
                .setName("Raid Boss")
                .setHealth(50000)
                .setDamage(500)
                .setDefense(250)
                .setSpeed(50)
                .setElement("NONE")
                .setAI(factory.createAIBehavior())
                .setAbilities(factory.createAbilities())
                .setLootTable(factory.createLootTable())
                .addPhase(1, 50000)
                .addPhase(2, 30000)
                .addPhase(3, 15000)
                .build();
    }
}
