// SuperFire_prop.java
package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.strategypattern.CircleShootStrategy;
import edu.hitsz.aircraft.strategypattern.DirectShootStrategy;
import edu.hitsz.aircraft.strategypattern.Strategy;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 超级火力道具
 * 永久提升子弹威力和子弹数量
 * @author hitsz
 */
public class SuperFire_prop extends AbstractProp {

    private static final long DURATION_MS = 10000;  // 10秒
    private static final int BULLET_COUNT = 10;
    private static final int BULLET_SPEED = 5;

    // 静态变量，记录当前正在生效的临时效果
    private static Timer currentTimer = null;
    private static Strategy previousStrategy = null;
    private static int previousShootNum = 1;
    private static AbstractAircraft previousAircraft = null;

    public SuperFire_prop(int locationX, int locationY, int speedX, int speedY) {
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

        // 应用新效果（环射时子弹数量由策略决定，不需要改 shootNum）
        hero.setShootStrategy(new CircleShootStrategy(true, BULLET_COUNT, BULLET_SPEED));

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
                System.out.println("环射效果结束");
                currentTimer = null;
            }
        }, DURATION_MS);

        System.out.println("💥 超级火力道具生效！360° 环射模式开启！");
    }

    @Override
    public BufferedImage getImage() {
        return ImageManager.SUPERFIRE_PROP_IMAGE;
    }
}