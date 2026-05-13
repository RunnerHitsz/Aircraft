package edu.hitsz.template;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.factory.BossEnemyFactory;

/**
 * 简单难度：无法生成 Boss 敌机
 */
public class EasyBossGame extends BossGameTemplate {

    @Override
    protected boolean canSpawnBoss() {
        // 简单难度：永远不生成 Boss
        return false;
    }

    @Override
    protected BossEnemy createBoss(int stage) {
        return (BossEnemy) new BossEnemyFactory().createEnemy();
    }
}