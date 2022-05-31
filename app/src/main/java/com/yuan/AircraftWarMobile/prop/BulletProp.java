package com.yuan.AircraftWarMobile.prop;

import com.yuan.AircraftWarMobile.aircraft.AbstractAircraft;
import com.yuan.AircraftWarMobile.aircraft.HeroAircraft;
import com.yuan.AircraftWarMobile.bullet.BaseBullet;
import com.yuan.AircraftWarMobile.strategy.BulletPropContext;

import java.util.List;

public class BulletProp extends AbstractProp {

    /**
     * "add shotNum" or "scattering"
     */
    private String type;

    public BulletProp(int locationX, int locationY, int speedX, int speedY, String type) {
        super(locationX, locationY, speedX, speedY);
        this.type = type;
    }

    @Override
    public void active(HeroAircraft heroAircraft, List<AbstractAircraft> enemyAircraft, List<BaseBullet> enemyBullet) {
        BulletPropContext bulletPropContext = new BulletPropContext(type);
        bulletPropContext.active(heroAircraft);
        System.out.println("BulletPropActive!");
    }

    @Override
    public String getMusicType() {
        return "get_supply";
    }

}
