package edu.hitsz.aircraft;

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

    //每次射击发射子弹数量
    private int shootNum = 3;

    //子弹威力
    private int power = 20;

    //子弹射击方向 (向上发射：-1，向下发射：1)
    private int direction = 1;


    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        //射击周期
        this.ShootCycle = 40;
    }

    public int getShootCycle() {
        return ShootCycle;
    }

    public void setShootCycle(int shootCycle) {
        this.ShootCycle = shootCycle;
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = this.getSpeedY() + direction * 5;
        int speedY = 0;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedY, speedX, power);
            res.add(bullet);
        }
        return res;
    }

    /**
     * Boss 掉落多个道具
     */
    @Override
    public List<AbstractProp> dropProp() {
        List<AbstractProp> props = new LinkedList<>();

        if (Math.random() < PROP_DROP_RATE) {
            // 掉落 1-2 个道具
            int dropCount = 1 + (int) (Math.random() * MAX_DROP_COUNT);

            for (int i = 0; i < dropCount; i++) {
                // 道具在 Boss 周围随机偏移
                int offsetX = (int) (Math.random() * 60) - 30;
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
