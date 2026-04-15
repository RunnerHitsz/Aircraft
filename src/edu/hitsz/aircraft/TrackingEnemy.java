// TrackingEnemy.java
package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategypattern.TrackingShootStrategy;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.SimplePropFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 追踪敌机
 * 会追踪玩家位置，发射追踪子弹
 * @author hitsz
 */
public class TrackingEnemy extends AbstractAircraft {

    // 道具掉落概率（50%）
    private static final double PROP_DROP_RATE = 0.5;

    // 追踪速度（水平移动速度）
    private int trackingSpeed = 10;

    // 玩家引用
    private HeroAircraft hero;

    public TrackingEnemy(int locationX, int locationY, int speedX, int speedY, int hp, HeroAircraft hero) {
        super(locationX, locationY, speedX, speedY, hp);

        // 设置追踪敌机属性
        this.direction = 1;       // 向下射击
        this.shootNum = 2;        // 每次发射2发追踪子弹
        this.power = 10;          // 子弹威力
        this.ShootCycle = 35;     //射击周期
        this.hero = hero;

        // 设置追踪射击策略
        setShootStrategy(new TrackingShootStrategy(hero));
    }

    @Override
    public void forward() {
        super.forward();

        // 水平追踪，保持向下移动
        if (hero != null) {
            double heroX = hero.getLocationX();

            // 水平方向持续追踪
            if (locationX < heroX - 10) {
                locationX += trackingSpeed;
            } else if (locationX > heroX + 10) {
                locationX -= trackingSpeed;
            }
        }

// 边界限制
        locationX = Math.max(0, Math.min(locationX, Main.WINDOW_WIDTH));

        // 边界限制（不能超出左右边界）
        locationX = Math.max(0, Math.min(locationX, Main.WINDOW_WIDTH));

        // 超出底部消失
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        // 使用父类的 shoot() 方法，它会调用 TrackingShootStrategy
        return super.shoot();
    }

    /**
     * 掉落道具
     */
    @Override
    public List<AbstractProp> dropProp() {
        List<AbstractProp> props = new LinkedList<>();

        if (Math.random() < PROP_DROP_RATE) {
            AbstractProp prop = SimplePropFactory.createRandom4Prop(
                    (int) this.getLocationX(),
                    (int) this.getLocationY()
            );
            if (prop != null) {
                props.add(prop);
                System.out.println("追踪敌机掉落道具！");
            }
        }

        return props;
    }
}