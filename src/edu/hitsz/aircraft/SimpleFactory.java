package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class SimpleFactory {
    public static AbstractAircraft createEnemy(String type){
        switch(type){
            case "MobEnemy":
                return new MobEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    0,
                    10,
                    30
                );
            case "ShootingEnemy":
                return new ShootingEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        0,
                        5,  // 速度较慢，因为会射击
                        60  // 生命值稍高
                );
            case "QuickEnemy":
                return new QuickEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.QUICK_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        0,
                        20,  // 速度较块
                        30  // 生命值稍低
                );
            case "BossEnemy":
                return new BossEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        2,   // 横向移动
                        0,
                        200  // 生命值较高
                );
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
