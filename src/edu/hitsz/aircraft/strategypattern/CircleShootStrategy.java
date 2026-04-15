// CircleShootStrategy.java
package edu.hitsz.aircraft.strategypattern;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 环形射击策略
 * - 英雄机：360° 全方向环射
 * - Boss：向下 180° 半圆环射
 * @author chen
 */
public class CircleShootStrategy implements Strategy {

    private boolean isHero;           // 是否为英雄机
    private int bulletCount;          // 子弹数量
    private int bulletSpeed;          // 子弹速度

    /**
     * 构造函数
     * @param isHero 是否为英雄机（true=英雄机360°，false=Boss向下180°）
     * @param bulletCount 子弹数量
     * @param bulletSpeed 子弹速度
     */
    public CircleShootStrategy(boolean isHero, int bulletCount, int bulletSpeed) {
        this.isHero = isHero;
        this.bulletCount = bulletCount;
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public List<BaseBullet> shoot(int locationX, int locationY,
                                  int speedX, int speedY,
                                  int direction, int shootNum, int power) {
        List<BaseBullet> bullets = new LinkedList<>();

        int x = locationX;
        int y = locationY;

        if (isHero) {
            // 英雄机：360° 全方向环射
            bullets = heroCircleShoot(x, y, power);
        } else {
            // Boss：向下 180° 半圆环射
            bullets = bossDownwardShoot(x, y, direction, power);
        }

        return bullets;
    }

    /**
     * 英雄机：360° 全方向环射
     */
    private List<BaseBullet> heroCircleShoot(int x, int y, int power) {
        List<BaseBullet> bullets = new LinkedList<>();

        for (int i = 0; i < bulletCount; i++) {
            // 计算角度：0 到 2π 均匀分布
            double angle = 2 * Math.PI * i / bulletCount;

            // 计算速度分量
            int bulletSpeedX = (int) (bulletSpeed * Math.sin(angle));
            int bulletSpeedY = (int) (bulletSpeed * Math.cos(angle));

            // 创建英雄机子弹
            HeroBullet bullet = new HeroBullet(x, y, bulletSpeedX, bulletSpeedY, power);
            bullets.add(bullet);
        }

        return bullets;
    }

    /**
     * Boss：向下 180° 半圆环射（扇形）
     */
    private List<BaseBullet> bossDownwardShoot(int x, int y, int direction, int power) {
        List<BaseBullet> bullets = new LinkedList<>();

        // 向下射击的角度范围：-90° 到 90°（以向下为0°）
        // 角度范围：-90° 到 90°，即 180° 范围
        double startAngle = Math.toRadians(-90);
        double endAngle = Math.toRadians(90);

        for (int i = 0; i < bulletCount; i++) {
            // 计算角度：从 startAngle 到 endAngle 均匀分布
            double t = (double) i / (bulletCount - 1);
            double angle = startAngle + t * (endAngle - startAngle);

            // 计算速度分量
            int bulletSpeedX = (int) (bulletSpeed * Math.sin(angle));
            int bulletSpeedY = (int) (bulletSpeed * Math.cos(angle)) * direction;

            // 创建敌机子弹
            EnemyBullet bullet = new EnemyBullet(x, y, bulletSpeedX, bulletSpeedY, power);
            bullets.add(bullet);
        }

        System.out.println("Boss 向下 180° 环射，发射 " + bulletCount + " 发子弹");
        return bullets;
    }
}