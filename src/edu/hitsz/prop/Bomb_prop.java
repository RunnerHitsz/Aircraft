package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.observer.PropSubject;

import java.awt.image.BufferedImage;

public class Bomb_prop extends PropSubject {

    public Bomb_prop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(HeroAircraft hero) {
        System.out.println("多看一眼就会爆炸！");
        notifyBombEffect();  // 通知所有观察者
    }

    @Override
    public BufferedImage getImage() {
        return ImageManager.BOMB_PROP_IMAGE;
    }
}
