package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.aircraft.QuickEnemy;
import edu.hitsz.aircraft.ShootingEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;


public class QuickEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {

        int rand = (int) (Math.random() * 2);
        int speedX = 10;
        if (rand == 1){
            speedX = -speedX;
        }

        return new QuickEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.QUICK_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                speedX,
                10,  // 速度较块
                30  // 生命值稍低
        );
    }
}
