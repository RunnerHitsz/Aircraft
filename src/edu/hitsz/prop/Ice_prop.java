package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.observer.PropSubject;

import java.awt.image.BufferedImage;

public class Ice_prop extends PropSubject {

    public Ice_prop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {
        System.out.println("寒冰，永不腐朽！");
        notifyIceEffect();  // 通知所有观察者
    }

    @Override
    public BufferedImage getImage() {
        return ImageManager.ICE_PROP_IMAGE;
    }
}
