package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;

public class Blood_prop extends AbstractProp {
    private static final int HEAL_AMOUNT = 30;  // 恢复血量

    public Blood_prop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {
        int newHp = hero.getHp() + HEAL_AMOUNT;
        // 不超过最大血量（假设最大100）
        hero.increaseHp(HEAL_AMOUNT);
        System.out.println("获得血量道具，恢复 " + HEAL_AMOUNT + " 点生命值，当前血量: " + hero.getHp());
    }

    @Override
    public BufferedImage getImage() {
        return ImageManager.BLOOD_PROP_IMAGE;
    }
}
