package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;

public class Bomb_prop extends AbstractProp{

    public Bomb_prop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {
        System.out.println("多看一眼就会爆炸！");
    }

    @Override
    public BufferedImage getImage() {
        return ImageManager.BOMB_PROP_IMAGE;
    }
}
