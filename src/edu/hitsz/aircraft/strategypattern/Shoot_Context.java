package edu.hitsz.aircraft.strategypattern;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class Shoot_Context {

    private Strategy strategy;

    public Shoot_Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {return strategy;}

    public List<BaseBullet> executeStrategy(int locationX, int locationY,
                                            int speedX, int speedY,
                                            int direction, int shootNum, int power){
        return strategy.shoot(locationX, locationY, speedX, speedY, direction, shootNum, power);
    }

}
