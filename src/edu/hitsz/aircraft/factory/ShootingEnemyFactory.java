package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.aircraft.ShootingEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;


public class ShootingEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {

        int rand = (int) (Math.random() * 2);
        int speedX = 2;
        if (rand == 1){
            speedX = -speedX;
        }


        return new ShootingEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                speedX,
                3,  // 速度较慢，因为会射击
                60  // 生命值稍高
        );
    }
}
