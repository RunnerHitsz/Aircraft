package edu.hitsz.prop;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.application.Main;
import edu.hitsz.aircraft.HeroAircraft;


/**
 * 道具基类
 * @author chen
 */
public abstract class AbstractProp extends  AbstractFlyingObject {
    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /**
     * 道具生效效果
     * @param hero 英雄机
     */
    public abstract void effect(HeroAircraft hero);

    @Override
    public void forward() {
        this.locationY += this.speedY;
        // 超出屏幕底部则消失
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
