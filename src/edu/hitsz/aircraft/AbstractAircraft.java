package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategypattern.Strategy;
import edu.hitsz.aircraft.strategypattern.Shoot_Context;
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

    //策略模式
    protected Shoot_Context shoot_context;

    // 射击参数
    protected int shootNum = 1;
    protected int power = 30;
    protected int direction = -1;  // -1向上，1向下

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    /**
     * 设置射击策略
     */
    public void setShootStrategy(Strategy strategy) {
        if (shoot_context == null) {
            shoot_context = new Shoot_Context(strategy);
        } else {
            shoot_context.setStrategy(strategy);
        }
    }

    public Strategy getShootStrategy() {
        if (shoot_context == null) {
            return null;
        }
        return shoot_context.getStrategy();
    }

    /**
     * 执行射击（使用上下文）
     */
    public List<BaseBullet> shoot() {
        if (shoot_context == null) {
            return new LinkedList<>();
        }

        return shoot_context.executeStrategy(
                getLocationX(), getLocationY(),
                getSpeedX(), getSpeedY(),
                direction, shootNum, power
        );
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



    // Getter/Setter
    public int getShootNum() { return shootNum; }
    public void setShootNum(int shootNum) { this.shootNum = shootNum; }
    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; }
    public int getDirection() { return direction; }
    public void setDirection(int direction) { this.direction = direction; }
    public int getHp() {
        return hp;
    }
    public void setMaxHp(int MaxHp){ this.maxHp = MaxHp; this.hp = maxHp; }
    public int getShootCycle() { return ShootCycle; }
    public void setShootCycle(int cycle) { this.ShootCycle = cycle; }

    /**
     * 敌机死亡时掉落道具（默认实现，不掉落）
     * 子类可以重写此方法来掉落道具
     * @return 掉落道具列表
     */
    public List<AbstractProp> dropProp() {
        return new LinkedList<>();  // 默认不掉落道具
    }
}


