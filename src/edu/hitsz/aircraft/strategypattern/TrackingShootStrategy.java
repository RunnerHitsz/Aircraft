// TrackingShootStrategy.java
package edu.hitsz.aircraft.strategypattern;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 追踪射击策略
 * 子弹会瞄准玩家当前位置
 * @author chen
 */
public class TrackingShootStrategy implements Strategy {

    private HeroAircraft hero;  // 玩家飞机引用

    public TrackingShootStrategy(HeroAircraft hero) {
        this.hero = hero;
    }

    @Override
    public List<BaseBullet> shoot(int locationX, int locationY,
                                  int speedX, int speedY,
                                  int direction, int shootNum, int power) {
        List<BaseBullet> bullets = new LinkedList<>();

        if (hero == null) {
            return bullets;
        }

        int x = locationX;
        int y = locationY + direction * 15;
        int bulletSpeed = 6;

        for (int i = 0; i < shootNum; i++) {
            // 计算指向玩家的方向向量
            double dx = hero.getLocationX() - x;
            double dy = hero.getLocationY() - y;
            double length = Math.sqrt(dx * dx + dy * dy);

            int speedXbullet, speedYbullet;

            if (length > 0) {
                // 归一化并乘以速度
                speedXbullet = (int) (bulletSpeed * dx / length);
                speedYbullet = (int) (bulletSpeed * dy / length);
            } else {
                // 玩家在敌机位置，向下发射
                speedXbullet = 0;
                speedYbullet = bulletSpeed * direction;
            }

            // 子弹横向分散
            int offsetX = (i * 2 - shootNum + 1) * 8;

            EnemyBullet bullet = new EnemyBullet(x + offsetX, y, speedXbullet, speedYbullet, power);
            bullets.add(bullet);
        }

        return bullets;
    }
}