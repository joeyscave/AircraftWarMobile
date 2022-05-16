package com.yuan.AircraftWarMobile.settings;

public class SettingsEasy extends Settings {
    @Override
    public void set() {
        enemyBlood = 30;
        enemySpeed = 10;
        enemyBulletPower = 10;
        heroBulletPower = 30;
        eliteEnemyEmergeProb = 0.3;
        bossEnemyEmergeScore = 99999999;
        bossBloodRaise=0;
    }

    @Override
    public void harder() {
        return;
    }
}
