// RandomEnemyFactory.java
package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * 随机敌机工厂（根据概率返回不同的具体工厂）
 * @author hitsz
 */
public class RandomEnemyFactory {

    // 各类型敌机的概率权重
    private static final int MOB_WEIGHT = 60;      // 普通敌机 60%
    private static final int SHOOTING_WEIGHT = 25; // 射击敌机 25%
    private static final int QUICK_WEIGHT = 12;    // 快速敌机 12%
    private static final int BOSS_WEIGHT = 3;      // Boss敌机 3%

    private static final int TOTAL_WEIGHT = MOB_WEIGHT + SHOOTING_WEIGHT + QUICK_WEIGHT + BOSS_WEIGHT;

    /**
     * 随机获取一个敌机工厂
     * @return 随机工厂实例
     */
    public static EnemyFactory getRandomFactory() {
        int rand = (int) (Math.random() * TOTAL_WEIGHT);

        if (rand < MOB_WEIGHT) {
            return new MobEnemyFactory();
        } else if (rand < MOB_WEIGHT + SHOOTING_WEIGHT) {
            return new ShootingEnemyFactory();
        } else if (rand < MOB_WEIGHT + SHOOTING_WEIGHT + QUICK_WEIGHT) {
            return new QuickEnemyFactory();
        } else {
            return new BossEnemyFactory();
        }
    }

    /**
     * 根据类型获取指定工厂
     * @param type 敌机类型
     * @return 对应的工厂实例
     */
    public static EnemyFactory getFactory(EnemyType type) {
        switch (type) {
            case MOB:
                return new MobEnemyFactory();
            case SHOOTING:
                return new ShootingEnemyFactory();
            case QUICK:
                return new QuickEnemyFactory();
            case BOSS:
                return new BossEnemyFactory();
            default:
                throw new IllegalArgumentException("未知的敌机类型: " + type);
        }
    }
}