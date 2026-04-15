package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.TrackingEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class TrackingEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy() {

        HeroAircraft hero = HeroAircraft.getHeroAircraft();

        return new TrackingEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.TRACKING_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,     // 水平速度（左右移动追踪）
                4,          // 垂直速度（向下移动）
                45,         // 生命值
                hero        // 玩家引用（用于追踪）
        );
    }
}