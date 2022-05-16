package com.yuan.AircraftWarMobile.prop;

import com.yuan.AircraftWarMobile.aircraft.AbstractAircraft;
import com.yuan.AircraftWarMobile.aircraft.HeroAircraft;
import com.yuan.AircraftWarMobile.basic.AbstractFlyingObject;
import com.yuan.AircraftWarMobile.bullet.BaseBullet;

import java.util.List;

public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }


    /**
     * 道具生效
     */
    public void active(HeroAircraft heroAircraft, List<AbstractAircraft> enemyAircraft, List<BaseBullet> enemyBullet) {
    }

    public String getMusicType(){
        return null;
    }
}
