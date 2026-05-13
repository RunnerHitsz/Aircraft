package edu.hitsz.template;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.manager.AudioManager;
import edu.hitsz.manager.AudioPath;

import java.util.List;

/**
 * Boss 生成模板类
 * 定义 Boss 生成的算法骨架
 */
public abstract class BossGameTemplate {

    protected boolean bossExist = false;
    protected int bossIndex = 0;
    protected int currentScore = 0;
    protected List<AbstractAircraft> enemyAircrafts;
    protected int nextBossScore = 300;
    protected AudioManager audioManager;
    protected boolean bossMusicPlaying = false;

    public BossGameTemplate() {
        audioManager = AudioManager.getInstance();
    }

    // 模板方法 
    public final void init(List<AbstractAircraft> enemyAircrafts) {
        this.enemyAircrafts = enemyAircrafts;
    }

    public final void updateScore(int score) {
        this.currentScore = score;
        if (canSpawnBoss()) {
            spawnBoss();
        }
    }

    // 模板方法：Boss 生成流程
    protected final void spawnBoss() {
        int stage = bossIndex + 1;

        beforeBossSpawn(stage);

        BossEnemy boss = createBoss(stage);
        enemyAircrafts.add(boss);
        bossExist = true;

        // 音乐切换
        if (!bossMusicPlaying) {
            audioManager.playBgm(AudioPath.BGM_BOSS, true);
            bossMusicPlaying = true;
            System.out.println("Boss 出场，切换为 Boss 音乐");
        }

        afterBossSpawn(stage);

        System.out.println("⚠️ 第" + stage + "只Boss出现！分数: " + currentScore);
    }


    //  抽象方法（子类必须实现） 
    protected abstract BossEnemy createBoss(int stage);

    //   钩子方法（子类可选实现）  
    protected void beforeBossSpawn(int stage) {}
    protected void afterBossSpawn(int stage) {}

    //   钩子方法：判断是否可以生成 Boss  
    protected boolean canSpawnBoss() {
        return currentScore >= nextBossScore && !bossExist && !isBossOnField();
    }

    //   公共方法  
    public void onBossDeath() {
        bossExist = false;
        bossIndex++;
        nextBossScore = 300 + bossIndex * 300;

        if (bossMusicPlaying) {
            audioManager.playBgm(AudioPath.BGM_GAME, true);
            bossMusicPlaying = false;
            System.out.println("Boss 被击败，恢复游戏音乐");
        }

        System.out.println("Boss被击败！下次Boss在 " + nextBossScore + " 分出现");
    }

    public boolean isBossExist() {
        return bossExist;
    }

    public void reset() {
        bossExist = false;
        bossIndex = 0;
        nextBossScore = 300;
    }

    private boolean isBossOnField() {
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (enemy instanceof BossEnemy && !enemy.notValid()) {
                return true;
            }
        }
        return false;
    }
}