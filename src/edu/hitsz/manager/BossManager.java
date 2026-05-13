package edu.hitsz.manager;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.Difficulty;
import edu.hitsz.template.BossGameTemplate;
import edu.hitsz.template.EasyBossGame;
import edu.hitsz.template.NormalBossGame;
import edu.hitsz.template.HardBossGame;

import java.util.List;

public class BossManager {

    private static BossManager instance;

    private BossGameTemplate bossGame;  // 模板对象
    private Difficulty difficulty;
    private List<AbstractAircraft> enemyAircrafts;

    private BossManager() {}

    public static BossManager getInstance() {
        if (instance == null) {
            instance = new BossManager();
        }
        return instance;
    }

    /**
     * 设置难度（必须在 init 之前调用）
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        // 根据难度选择对应的模板实现
        switch (difficulty) {
            case EASY:
                bossGame = new EasyBossGame();
                break;
            case NORMAL:
                bossGame = new NormalBossGame();
                break;
            case HARD:
                bossGame = new HardBossGame();
                break;
            default:
                bossGame = new NormalBossGame();
        }
    }

    public void init(List<AbstractAircraft> enemyAircrafts) {
        this.enemyAircrafts = enemyAircrafts;
        if (bossGame != null) {
            bossGame.init(enemyAircrafts);
        }
    }

    public void updateScore(int score) {
        if (bossGame != null) {
            bossGame.updateScore(score);
        }
    }

    public void onBossDeath() {
        if (bossGame != null) {
            bossGame.onBossDeath();
        }
    }

    public boolean isBossExist() {
        return bossGame != null && bossGame.isBossExist();
    }

    public void reset() {
        if (bossGame != null) {
            bossGame.reset();
        }
    }
}