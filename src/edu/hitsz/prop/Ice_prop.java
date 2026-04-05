package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

import java.awt.image.BufferedImage;

public class Ice_prop extends AbstractProp{

    public Ice_prop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {
        System.out.println("寒冰，永不腐朽！");
    }

    @Override
    public BufferedImage getImage() {
        return ImageManager.ICE_PROP_IMAGE;
    }
}
