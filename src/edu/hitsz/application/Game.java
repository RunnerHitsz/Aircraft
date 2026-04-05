package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.factory.EnemyFactory;
import edu.hitsz.aircraft.factory.RandomEnemyFactory;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.AbstractProp;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    //调度器, 用于定时任务调度
    private final Timer timer;
    //时间间隔(ms)，控制刷新频率
    private final int timeInterval = 30;

    //简单工程创建敌机
    SimpleFactory simpleFactory;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;

    // 道具列表
    private final List<AbstractProp> props;

    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    //敌机生成周期
    protected double enemySpawnCycle  =  20;
    private int enemySpawnCounter = 0;

    //英雄机和敌机射击周期计数
    private int hero_shootCounter = 0;

    //当前玩家分数
    private int score = 0;

    //游戏结束标志
    private boolean gameOverFlag = false;

    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();

        props = new LinkedList<>();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                enemySpawnCounter++;
                if (enemySpawnCounter >=enemySpawnCycle) {
                    enemySpawnCounter = 0;

                    if (enemyAircrafts.size() < enemyMaxNumber) {
                        //使用工厂方法模式：先获取随机工厂，再创建敌机
                        EnemyFactory factory = RandomEnemyFactory.getRandomFactory();
                        AbstractAircraft newEnemy = factory.createEnemy();
                        enemyAircrafts.add(newEnemy);
                    }

                }

                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                // 撞击检测
                crashCheckAction();
                // 后处理
                postProcessAction();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task,0,timeInterval);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void shootAction() {
        // 1. 英雄机射击
        if (heroAircraft.updateAndCheckShoot()) {
            heroBullets.addAll(heroAircraft.shoot());
        }

        //Todo 敌机射击
        //2. 敌机射击（统一处理，不需要类型判断）
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (enemy.updateAndCheckShoot()) {
                enemyBullets.addAll(enemy.shoot());
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄机
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }

            if (heroAircraft.crash(bullet)) {
                // 英雄撞击到敌机子弹
                // 英雄损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    // 根据敌机类型获得不同分数
                    // TODO 获得分数，产生道具补给
                      //判断敌机是否死亡
                    if (enemyAircraft.notValid()) {
                        // 根据敌机类型获得不同分数
                        if (enemyAircraft instanceof MobEnemy) {
                            score += 10;
                        } else if (enemyAircraft instanceof ShootingEnemy) {
                            score += 20;
                        } else if (enemyAircraft instanceof QuickEnemy) {
                            score += 10;
                        } else if (enemyAircraft instanceof BossEnemy) {
                            score += 100;
                        }

                        //只在死亡时调用 dropProp
                        List<AbstractProp> droppedProps = enemyAircraft.dropProp();
                        props.addAll(droppedProps);
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                prop.effect(heroAircraft);  // 道具生效
                prop.vanish();               // 道具消失
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        // Todo: 删除无效道具
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            System.out.println("Game Over!");
        }
    };

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 按score绘制背景,图片滚动
        if (score < 200){
            g.drawImage(ImageManager.BACKGROUND_IMAGE1, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.BACKGROUND_IMAGE1, 0, this.backGroundTop, null);
        }
        else if(score < 400){
            g.drawImage(ImageManager.BACKGROUND_IMAGE2, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.BACKGROUND_IMAGE2, 0, this.backGroundTop, null);
        }
        else if(score < 600){
            g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop, null);
        }
        else if(score < 800){
            g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.BACKGROUND_IMAGE3, 0, this.backGroundTop, null);
        }
        else if(score < 1000){
            g.drawImage(ImageManager.BACKGROUND_IMAGE4, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.BACKGROUND_IMAGE4, 0, this.backGroundTop, null);
        }
        else{
            g.drawImage(ImageManager.BACKGROUND_IMAGE5, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.BACKGROUND_IMAGE5, 0, this.backGroundTop, null);
        }

        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);

        // Todo: 绘制道具
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}
