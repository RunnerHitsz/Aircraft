// SuperFire_prop.java
package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;

/**
 * 超级火力道具
 * 永久提升子弹威力和子弹数量
 * @author hitsz
 */
public class SuperFire_prop extends AbstractProp {

    private static final int EXTRA_BULLETS = 1;
    private static final int DECREASE_CYCLE = 1;
    private static final int MAX_SHOOT_NUM = 5;
    private static final int MIN_SHOOT_CYCLE = 10;

    public SuperFire_prop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {

        // 提升子弹数量
        int currentShootNum = hero.getShootNum();
        int newShootNum = Math.min(currentShootNum + EXTRA_BULLETS, MAX_SHOOT_NUM);
        hero.setShootNum(newShootNum);

        //提升射击频率
        int currentCycle = hero.getShootCycle();
        int newShootCycle = Math.max(currentCycle - DECREASE_CYCLE, MIN_SHOOT_CYCLE);
        hero.setShootCycle(newShootCycle);

        System.out.println("超级火力道具生效！");
        System.out.println("  子弹数量: " + currentShootNum + " → " + newShootNum);

        System.out.println("超级火力道具生效！");
        System.out.println("  设计频率: " + currentCycle + " → " + newShootCycle);
    }

    @Override
    public BufferedImage getImage() {
        return ImageManager.SUPERFIRE_PROP_IMAGE;
    }
}