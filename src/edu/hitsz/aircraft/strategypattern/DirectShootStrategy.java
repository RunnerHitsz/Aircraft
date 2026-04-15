// DirectShootStrategy.java
package edu.hitsz.aircraft.strategypattern;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 直线射击策略
 * @author chen
 */
public class DirectShootStrategy implements Strategy {

    // 是否为英雄机子弹（用于区分创建 HeroBullet 还是 EnemyBullet）
    private boolean isHero;

    public DirectShootStrategy(boolean isHero) {
        this.isHero = isHero;
    }

    @Override
    public List<BaseBullet> shoot(int locationX, int locationY,
                                  int speedX, int speedY,
                                  int direction, int shootNum, int power) {
        List<BaseBullet> bullets = new LinkedList<>();

        int x = locationX;
        int y = locationY + direction * 2;

        // 子弹速度
        int bulletSpeedX = 0;
        int bulletSpeedY = speedY + direction * 5;

        for (int i = 0; i < shootNum; i++) {
            // 子弹横向分散
            int offsetX = (i * 2 - shootNum + 1) * 10;

            BaseBullet bullet;
            if (isHero) {
                bullet = new HeroBullet(x + offsetX, y, bulletSpeedX, bulletSpeedY, power);
            } else {
                bullet = new EnemyBullet(x + offsetX, y, bulletSpeedX, bulletSpeedY, power);
            }
            bullets.add(bullet);
        }

        return bullets;
    }
}