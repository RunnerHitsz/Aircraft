package edu.hitsz.application;


import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    //背景图片
    public static BufferedImage BACKGROUND_IMAGE1;
    public static BufferedImage BACKGROUND_IMAGE2;
    public static BufferedImage BACKGROUND_IMAGE3;
    public static BufferedImage BACKGROUND_IMAGE4;
    public static BufferedImage BACKGROUND_IMAGE5;

    //飞机图片
    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage SHOOTING_ENEMY_IMAGE;
    public static BufferedImage Quick_ENEMY_IMAGE;
    public static BufferedImage Boss_ENEMY_IMAGE;

    // 道具图片
    public static BufferedImage BLOOD_PROP_IMAGE;    // 血量道具图片
    public static BufferedImage FIRE_PROP_IMAGE;    // 火力道具图片
    public static BufferedImage SUPERFIRE_PROP_IMAGE;    // 火力道具图片
    public static BufferedImage BOMB_PROP_IMAGE;    // 火力道具图片
    public static BufferedImage ICE_PROP_IMAGE;    // 火力道具图片

    static {
        try {

            BACKGROUND_IMAGE1 = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            BACKGROUND_IMAGE2 = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            BACKGROUND_IMAGE3 = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
            BACKGROUND_IMAGE4 = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
            BACKGROUND_IMAGE5 = ImageIO.read(new FileInputStream("src/images/background_kobe.png"));

            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            SHOOTING_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            //SHOOTING_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/kunkun.png"));
            Quick_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            Boss_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));

            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));
            //ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/basketball.png"));

            BLOOD_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            FIRE_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            SUPERFIRE_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bulletPlus.png"));
            BOMB_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            ICE_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_freeze.png"));

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(ShootingEnemy.class.getName(), SHOOTING_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(QuickEnemy.class.getName(), Quick_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), Boss_ENEMY_IMAGE);

            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);

            CLASSNAME_IMAGE_MAP.put(Blood_prop.class.getName(), BLOOD_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Fire_prop.class.getName(), FIRE_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(SuperFire_prop.class.getName(), SUPERFIRE_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Bomb_prop.class.getName(), BOMB_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Ice_prop.class.getName(), ICE_PROP_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

}
