package com.yuan.AircraftWarMobile.strategy;

import com.yuan.AircraftWarMobile.aircraft.HeroAircraft;
import com.yuan.AircraftWarMobile.application.Main;

public class BulletPropScattering implements BulletPropAbstractStrategy {
    @Override
    public void active(HeroAircraft heroAircraft) {
        Thread t = new Thread(() -> {
            synchronized (Main.Bullet_LOCK) {
                heroAircraft.setIncreaseShootNum(1);
                heroAircraft.reverseScattering();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                heroAircraft.setIncreaseShootNum(0);
                heroAircraft.reverseScattering();
            }
        });
        t.start();
    }
}
