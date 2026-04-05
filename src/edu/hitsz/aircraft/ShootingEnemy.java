package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.SimplePropFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 射击敌机
 * 可射击、掉落道具
 * @author chen
 */

public class ShootingEnemy extends AbstractAircraft {

    // 道具掉落概率（0-1之间）
    private static final double PROP_DROP_RATE = 0.2;  // 20%概率掉落道具

    //每次射击发射子弹数量
    private int shootNum = 3;

    //子弹威力
    private int power = 20;

    //子弹射击方向 (向上发射：-1，向下发射：1)
    private int direction = 1;


    public ShootingEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        //射击周期
        this.ShootCycle = 35;
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
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    //直线发射
    /*
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
    */
    //扇形发射
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 20;
        for (int i = 0; i < shootNum; i++) {
            // 计算扇形角度：从 -30° 到 +30°
            double angle = Math.toRadians(-30 + (60.0 / (shootNum - 1)) * i);

            // 根据角度计算速度分量
            int bulletSpeed = 8;  // 子弹速度
            int speedX = (int) (bulletSpeed * Math.sin(angle));
            int speedY = (int) (bulletSpeed * Math.cos(angle)) * direction;

            EnemyBullet bullet = new EnemyBullet(x, y, speedX, speedY, power);
            res.add(bullet);
        }

        return res;
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
                System.out.println("王牌敌机掉落道具！");
            }
        }

        return props;
    }

}
