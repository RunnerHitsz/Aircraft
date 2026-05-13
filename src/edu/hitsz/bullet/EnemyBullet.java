package edu.hitsz.bullet;

import edu.hitsz.observer.PropObserver;

/**
 * 敌机子弹
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements PropObserver {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    private boolean isFrozen = false;
    private int frozenRemaining = 0;

    @Override
    public void onBombEffect() {
        // 炸弹效果：直接消失
        this.vanish();
    }

    @Override
    public void onIceEffect() {
        // 冰冻效果：静止5秒后恢复
        this.isFrozen = true;
        this.frozenRemaining = 5 * 60;
        this.speedX = 0;
        this.speedY = 0;
    }

    @Override
    public void forward() {
        if (isFrozen) {
            frozenRemaining--;
            if (frozenRemaining <= 0) {
                isFrozen = false;
            }
        } else {
            super.forward();
        }
    }
}
