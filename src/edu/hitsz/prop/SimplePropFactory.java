// SimplePropFactory.java
package edu.hitsz.prop;

public class SimplePropFactory {

    private static final int PROP_SPEED = 5;

    private SimplePropFactory() {}

    /**
     * 根据类型创建道具（工厂只负责创建）
     * @param type 道具类型
     * @param x 生成X坐标
     * @param y 生成Y坐标
     * @return 道具实例
     */
    public static AbstractProp createProp(String type, int x, int y) {
        switch(type) {
            case "Blood_prop":
                return new Blood_prop(x, y, 0, PROP_SPEED);
            case "Fire_prop":
                return new Fire_prop(x, y, 0, PROP_SPEED);
            case "SuperFire_prop":
                return new SuperFire_prop(x, y, 0, PROP_SPEED);
            case "Bomb_prop":
                return new Bomb_prop(x, y, 0, PROP_SPEED);
            case "Ice_prop":
                return new Ice_prop(x, y, 0, PROP_SPEED);
            default:
                return null;  // 未知类型返回null
        }
    }

    /**
     * 随机创建道具
     */
    //产生四个道具
    public static AbstractProp createRandom4Prop(int x, int y) {
        // 道具类型数组
        String[] types = {"Blood_prop", "Fire_prop", "SuperFire_prop", "Bomb_prop"};

        // 权重数组（概率权重，总和不必为1，越大概率越高）
        int[] weights = {40, 30, 20, 10};  // 血40%，火30%，超火20%，炸弹10%

        // 计算总权重
        int totalWeight = 0;
        for (int w : weights) {
            totalWeight += w;
        }

        // 随机选择
        int rand = (int) (Math.random() * totalWeight);
        int cumulative = 0;

        for (int i = 0; i < types.length; i++) {
            cumulative += weights[i];
            if (rand < cumulative) {
                return createProp(types[i], x, y);
            }
        }

        return createProp(types[0], x, y);  // 默认返回第一个
    }

    //产生五个道具
    public static AbstractProp createRandom5Prop(int x, int y) {
        // 道具类型数组
        String[] types = {"Blood_prop", "Fire_prop", "SuperFire_prop", "Bomb_prop", "Ice_prop"};

        // 权重数组（越大概率越高）
        int[] weights = {40, 25, 15, 10, 10};  // 血40%，火25%，超火15%，炸弹10%，冰10%

        // 计算总权重
        int totalWeight = 0;
        for (int w : weights) {
            totalWeight += w;
        }

        // 随机选择
        int rand = (int) (Math.random() * totalWeight);
        int cumulative = 0;

        for (int i = 0; i < types.length; i++) {
            cumulative += weights[i];
            if (rand < cumulative) {
                return createProp(types[i], x, y);
            }
        }

        return createProp(types[0], x, y);
    }
}