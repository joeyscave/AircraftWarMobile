package com.yuan.AircraftWarMobile.strategy;

import com.yuan.AircraftWarMobile.aircraft.HeroAircraft;

public class BulletPropContext {

    private BulletPropAbstractStrategy bulletPropAbstractStrategy;

    public BulletPropContext(String type) {
        switch (type) {
            case "add shotNum":
                bulletPropAbstractStrategy=new BulletPropAddShotNum(2);
                break;
            case "scattering":
                bulletPropAbstractStrategy=new BulletPropScattering();
                break;
            default:
                break;
        }
    }

    public void active(HeroAircraft heroAircraft){
        bulletPropAbstractStrategy.active(heroAircraft);
    }
}
