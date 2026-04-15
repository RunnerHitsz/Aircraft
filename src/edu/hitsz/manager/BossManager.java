package edu.hitsz.manager;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.factory.BossEnemyFactory;

import java.util.List;

public class BossManager {

    private static BossManager instance;

    private boolean bossExist = false;
    private int bossIndex = 0;              // 当前是第几只Boss（从0开始）
    private int currentScore = 0;

    private List<AbstractAircraft> enemyAircrafts;

    // Boss出现分数间隔
    private int nextBossScore = 100;        // 第一只100分

    private BossManager() {}

    public static BossManager getInstance() {
        if (instance == null) {
            instance = new BossManager();
        }
        return instance;
    }

    public void init(List<AbstractAircraft> enemyAircrafts) {
        this.enemyAircrafts = enemyAircrafts;
    }

    public void updateScore(int score) {
        this.currentScore = score;

        if (currentScore >= nextBossScore && !bossExist && !isBossOnField()) {
            spawnBoss();
        }
    }

    private void spawnBoss() {
        //  设计Boss参数
        int stage = bossIndex + 1;  // 第几只Boss（1, 2, 3...）

        int hp = 200 + 50 * stage;           // 200, 250, 300, 350, 400...
        int power = 20 + 5 * stage;          // 20, 25, 30, 35, 40...
        int shootNum = 5 + stage / 2;        // 5, 5, 6, 6, 7...

        // 创建Boss并设置参数
        BossEnemy boss = (BossEnemy) new BossEnemyFactory().createEnemy();
        boss.setMaxHp(hp);
        boss.setPower(power);
        boss.setShootNum(shootNum);

        enemyAircrafts.add(boss);
        bossExist = true;

        System.out.println("========================");
        System.out.println("⚠️ 第" + stage + "只Boss出现！分数: " + currentScore);
        System.out.println("   生命值: " + hp + "，攻击力: " + power + "，子弹数: " + shootNum);
        System.out.println("========================");
    }

    public void onBossDeath() {
        bossExist = false;
        bossIndex++;

        // 下次Boss出现分数：400, 700, 1000, 1300...
        nextBossScore = 100 + bossIndex * 300;

        System.out.println(" Boss被击败！下次Boss在 " + nextBossScore + " 分出现");
    }

    private boolean isBossOnField() {
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (enemy instanceof BossEnemy && !enemy.notValid()) {
                return true;
            }
        }
        return false;
    }

    public boolean isBossExist() {
        return bossExist;
    }

    public void reset() {
        bossExist = false;
        bossIndex = 0;
        nextBossScore = 100;
    }
}