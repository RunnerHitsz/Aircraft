package edu.hitsz.aircraft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeroAircraftTest {

    private HeroAircraft hero;

    @BeforeEach
    void setUp() {
        hero = HeroAircraft.getHeroAircraft();
        hero.setPower(30);  // 重置为默认值
    }

    @Test
    void getPower() {
        assertEquals(30, hero.getPower());
    }

    @Test
    void setPower() {
        hero.setPower(50);
        assertEquals(50, hero.getPower());
    }

    @Test
    void forward() {
        double x = hero.getLocationX();
        double y = hero.getLocationY();
        hero.forward();
        assertEquals(x, hero.getLocationX());
        assertEquals(y, hero.getLocationY());
    }
}