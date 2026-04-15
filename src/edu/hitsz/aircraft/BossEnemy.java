package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategypattern.DirectShootStrategy;
import edu.hitsz.aircraft.strategypattern.ScatterShootStrategy;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.Blood_prop;
import edu.hitsz.prop.SimplePropFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * boss敌机
 * 可射击、掉落道具
 * @author chen
 */

public class BossEnemy extends AbstractAircraft{

    // 道具掉落概率（0-1之间）
    private static final double PROP_DROP_RATE = 0.7;   // 70%掉落
    private static final int MAX_DROP_COUNT = 2;        // 最多掉落2个

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);

        this.ShootCycle = 40;
        this.shootNum = 20;
        this.power = 10;
        this.direction = 1;

        // 初始使用扇形射击
        setShootStrategy(new ScatterShootStrategy(false, 150));
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    /**
     * Boss 掉落多个道具
     */
    @Override
    public List<AbstractProp> dropProp() {
        List<AbstractProp> props = new LinkedList<>();

        if (Math.random() < PROP_DROP_RATE) {
            // 掉落 2-3 个道具
            int dropCount = 2 + (int) (Math.random() * MAX_DROP_COUNT);

            for (int i = 0; i < dropCount; i++) {
                // 道具在 Boss 周围随机偏移
                int offsetX = (int) (Math.random() * 90) - 30;
                AbstractProp prop = SimplePropFactory.createRandom5Prop(
                        this.getLocationX() + offsetX,
                        this.getLocationY()
                );
                if (prop != null) {
                    props.add(prop);
                }
            }

            System.out.println("Boss 掉落 " + props.size() + " 个道具！");
        }

        return props;
    }


}
