package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategypattern.DirectShootStrategy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    // 使用 volatile 确保多线程环境下的可见性和有序性
    private static volatile HeroAircraft heroAircraft;

    //私有化构造函数
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);

        this.ShootCycle = 20;
        this.shootNum = 1;
        this.power = 30;
        this.direction = -1;
        //设置射击策略：直线射击
        setShootStrategy(new DirectShootStrategy(true));
    }

    //双重检查锁定
    public static HeroAircraft getHeroAircraft() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 10000);
                }
            }
        }
        return heroAircraft;
    }

    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public int getShootNum() {
        return shootNum;
    }
    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }


    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

}
