package edu.hitsz.strategypattern;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface Strategy {
    List<BaseBullet> shoot(int locationX, int locationY,
                           int speedX, int speedY,
                           int direction, int shootNum, int power);
}
