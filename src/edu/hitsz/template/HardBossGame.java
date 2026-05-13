package edu.hitsz.template;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.factory.BossEnemyFactory;

/**
 * 困难难度：每次召唤 Boss 机时，提升 Boss 机血量
 */
public class HardBossGame extends BossGameTemplate {

    @Override
    protected BossEnemy createBoss(int stage) {
        BossEnemy boss = (BossEnemy) new BossEnemyFactory().createEnemy();

        // 困难难度：血量随 stage 增加
        int hp = 200 + 50 * stage;      // 250, 300, 350...
        int power = 20 + 50 * stage;     // 70, 120, 170...
        int shootNum = 5 + stage * 3;    // 8, 11, 14...

        boss.setMaxHp(hp);
        boss.setPower(power);
        boss.setShootNum(shootNum);

        return boss;
    }

    @Override
    protected void beforeBossSpawn(int stage) {
        System.out.println("困难难度：Boss 血量提升！第" + stage + "只");
    }
}