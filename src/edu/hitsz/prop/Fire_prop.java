package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.strategypattern.CircleShootStrategy;
import edu.hitsz.aircraft.strategypattern.DirectShootStrategy;
import edu.hitsz.aircraft.strategypattern.ScatterShootStrategy;
import edu.hitsz.aircraft.strategypattern.Strategy;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 火力道具
 * 永久提升英雄机子弹威力
 * @author hitsz
 */
public class Fire_prop extends AbstractProp {

    private static final long DURATION_MS = 10000;
    private static final int SCATTER_SHOOT_NUM = 3;
    private static final double SPREAD_ANGLE = 80;

    // 静态变量，记录当前正在生效的临时效果
    private static Timer currentTimer = null;
    private static Strategy previousStrategy = null;
    private static int previousShootNum = 1;
    private static AbstractAircraft previousAircraft = null;  // 记录是哪个飞机

    public Fire_prop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {
        // 取消之前的临时效果
        if (currentTimer != null) {
            currentTimer.cancel();
            System.out.println("重新计时，效果延长10秒");
        } else {
            // 第一次吃道具，保存当前状态
            previousStrategy = hero.getShootStrategy();
            previousShootNum = hero.getShootNum();
            previousAircraft = hero;
        }

        // 应用新效果
        hero.setShootNum(SCATTER_SHOOT_NUM);
        hero.setShootStrategy(new ScatterShootStrategy(true, SPREAD_ANGLE));

        // 开始计时
        currentTimer = new Timer();
        currentTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 恢复到之前的状态
                if (previousAircraft != null) {
                    previousAircraft.setShootNum(previousShootNum);
                    previousAircraft.setShootStrategy(previousStrategy);
                }
                System.out.println("散射效果结束");
                currentTimer = null;
            }
        }, DURATION_MS);

        System.out.println("🔥 散射模式开启！");
    }


    @Override
    public BufferedImage getImage() {
        return ImageManager.FIRE_PROP_IMAGE;
    }
}