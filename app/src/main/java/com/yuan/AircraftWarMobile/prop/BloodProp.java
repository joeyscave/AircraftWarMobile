package com.yuan.AircraftWarMobile.prop;

import com.yuan.AircraftWarMobile.aircraft.AbstractAircraft;
import com.yuan.AircraftWarMobile.aircraft.HeroAircraft;
import com.yuan.AircraftWarMobile.bullet.BaseBullet;

import java.util.List;

public class BloodProp extends AbstractProp {

    private int increaseHp;

    public BloodProp(int locationX, int locationY, int speedX, int speedY, int increaseHp) {
        super(locationX, locationY, speedX, speedY);
        this.increaseHp = increaseHp;
    }

    @Override
    public void active(HeroAircraft heroAircraft, List<AbstractAircraft> enemyAircraft, List<BaseBullet> enemyBullet) {
        heroAircraft.decreaseHp(-increaseHp);
        System.out.println("BloodPropActive!");
    }

    @Override
    public String getMusicType() {
        return "get_supply";
    }
}
