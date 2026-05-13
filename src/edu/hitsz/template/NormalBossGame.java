package edu.hitsz.template;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.factory.BossEnemyFactory;

/**
 * 普通难度：每次召唤 Boss 机时，不改变 Boss 机血量
 */
public class NormalBossGame extends BossGameTemplate {

    @Override
    protected BossEnemy createBoss(int stage) {
        BossEnemy boss = (BossEnemy) new BossEnemyFactory().createEnemy();

        // 普通难度：固定血量，不随 stage 增加
        int hp = 200;
        int power = 20;
        int shootNum = 8;

        boss.setMaxHp(hp);
        boss.setPower(power);
        boss.setShootNum(shootNum);

        return boss;
    }
}