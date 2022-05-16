package com.yuan.AircraftWarMobile.factory.aircraft;

import com.yuan.AircraftWarMobile.aircraft.AbstractAircraft;
import com.yuan.AircraftWarMobile.aircraft.MobEnemy;
import com.yuan.AircraftWarMobile.service.ImageManager;
import com.yuan.AircraftWarMobile.application.Main;

public class MobEnemyFactory implements AbstractAircraftFactory {
    @Override
    public AbstractAircraft create() {
        return new MobEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                0,
                Main.settings.enemySpeed,
                Main.settings.enemyBlood
        );
    }
}
