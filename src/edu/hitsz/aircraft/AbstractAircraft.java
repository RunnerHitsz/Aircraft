package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.AbstractProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 所有种类飞机的抽象父类
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {

    //最大生命值
    protected int maxHp;
    protected int hp;

    // 每个敌机自己的射击计数器
    protected int ShootCounter = 0;
    protected int ShootCycle = 30;


    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    /**
     * 更新射击计数器，并返回是否可以射击
     */
    public boolean updateAndCheckShoot() {
        ShootCounter++;
        if (ShootCounter >= ShootCycle) {
            ShootCounter = 0;
            return true;
        }
        return false;
    }

    /**
     * 设置射击周期
     */
    public void setShootCycle(int cycle) {
        this.ShootCycle = cycle;
    }

    /**
     * 获取射击周期
     */
    public int getShootCycle() {
        return ShootCycle;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void increaseHp(int increase){
        hp += increase;
        if(hp >= maxHp){
            hp=maxHp;
        }
    }

    public int getHp() {
        return hp;
    }


    /**
     * 飞机射击方法
     * @return
     *  可射击对象需实现，返回子弹列表
     *  非可射击对象空实现，返回空列表
     */
    public abstract List<BaseBullet> shoot();

    /**
     * 敌机死亡时掉落道具（默认实现，不掉落）
     * 子类可以重写此方法来掉落道具
     * @return 掉落道具列表
     */
    public List<AbstractProp> dropProp() {
        return new LinkedList<>();  // 默认不掉落道具
    }
}


