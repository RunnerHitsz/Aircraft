package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategypattern.DirectShootStrategy;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.Blood_prop;
import edu.hitsz.prop.SimplePropFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 快速敌机
 * 可射击、会掉落道具
 * @author chen
 */
public class QuickEnemy extends AbstractAircraft {

    // 道具掉落概率（0-1之间）
    private static final double PROP_DROP_RATE = 0.5;  // 50%概率掉落道具

    public QuickEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);

        this.ShootCycle = 35;
        this.direction = 1;       // 向下射击
        this.shootNum = 2;        // 每次发射2发子弹
        this.power = 20;          // 子弹威力
        //设置射击策略：直线射击
        setShootStrategy(new DirectShootStrategy(false));
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    /**
     * 当敌机死亡时，随机掉落道具
     * @return 掉落道具的列表（可能为空）
     */
    @Override
    public List<AbstractProp> dropProp() {
        List<AbstractProp> props = new LinkedList<>();

        // 50%概率掉落道具
        if (Math.random() < PROP_DROP_RATE) {
            // 随机创建一种道具
            AbstractProp prop = SimplePropFactory.createRandom4Prop(
                    this.getLocationX(),
                    this.getLocationY()
            );
            if (prop != null) {
                props.add(prop);
                System.out.println("精锐敌机掉落道具！");
            }
        }

        return props;
    }
}
