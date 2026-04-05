package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * 敌机工厂接口（工厂方法模式）
 * @author hitsz
 */
public interface EnemyFactory {
    /**
     * 创建敌机
     * @return 敌机实例
     */
    AbstractAircraft createEnemy();
}