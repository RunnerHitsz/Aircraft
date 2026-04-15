// ScatterShootStrategy.java
package edu.hitsz.aircraft.strategypattern;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 扇形射击策略
 * @author chen
 */
public class ScatterShootStrategy implements Strategy {

    private boolean isHero;
    private double spreadAngle;  // 扇形角度

    public ScatterShootStrategy(boolean isHero, double spreadAngle) {
        this.isHero = isHero;
        this.spreadAngle = spreadAngle;
    }

    @Override
    public List<BaseBullet> shoot(int locationX, int locationY,
                                  int speedX, int speedY,
                                  int direction, int shootNum, int power) {
        List<BaseBullet> bullets = new LinkedList<>();

        int x = locationX;
        int y = locationY + direction * 20;
        int bulletSpeed = 8;

        if (shootNum == 1) {
            // 单发子弹，直射
            BaseBullet bullet;
            if (isHero) {
                bullet = new HeroBullet(x, y, 0, direction * bulletSpeed, power);
            } else {
                bullet = new EnemyBullet(x, y, 0, direction * bulletSpeed, power);
            }
            bullets.add(bullet);
        } else {
            // 扇形散射
            double startAngle = Math.toRadians(-spreadAngle / 2);
            double angleStep = Math.toRadians(spreadAngle) / (shootNum - 1);

            for (int i = 0; i < shootNum; i++) {
                double angle = startAngle + angleStep * i;
                int bulletSpeedX = (int) (bulletSpeed * Math.sin(angle));
                int bulletSpeedY = (int) (bulletSpeed * Math.cos(angle)) * direction;

                BaseBullet bullet;
                if (isHero) {
                    bullet = new HeroBullet(x, y, bulletSpeedX, bulletSpeedY, power);
                } else {
                    bullet = new EnemyBullet(x, y, bulletSpeedX, bulletSpeedY, power);
                }
                bullets.add(bullet);
            }
        }

        return bullets;
    }
}