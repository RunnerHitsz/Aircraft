package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observer.PropObserver;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击、不掉落道具
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft implements PropObserver {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    private boolean isFrozen = false;

    @Override
    public void forward() {
        if(!isFrozen){
            super.forward();
            // 判定 y 轴向下飞行出界
            if (locationY >= Main.WINDOW_HEIGHT ) {
                vanish();
            }
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

    @Override
    public void onBombEffect() {
        // 炸弹效果：直接坠毁
        this.vanish();
    }

    @Override
    public void onIceEffect() {
        // 冰冻效果：永久静止
        this.isFrozen = true;
        this.speedX = 0;
        this.speedY = 0;
    }
}
