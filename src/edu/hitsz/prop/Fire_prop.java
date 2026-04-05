package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;

/**
 * 火力道具
 * 永久提升英雄机子弹威力
 * @author hitsz
 */
public class Fire_prop extends AbstractProp {

    // 火力提升值
    private static final int POWER_BOOST = 5;

    // 最大威力限制
    private static final int MAX_POWER = 120;

    public Fire_prop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {
        int currentPower = hero.getPower();
        int newPower = Math.min(currentPower + POWER_BOOST, MAX_POWER);
        hero.setPower(newPower);

        System.out.println("火力道具生效！子弹威力 " + currentPower + " → " + newPower);
    }

    @Override
    public BufferedImage getImage() {
        return ImageManager.FIRE_PROP_IMAGE;
    }
}