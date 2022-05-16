package com.yuan.AircraftWarMobile.settings;

public class SettingsHard extends Settings{
    @Override
    public void set() {
        enemyBlood = 40;
        enemySpeed = 12;
        enemyBulletPower = 12;
        heroBulletPower = 25;
        eliteEnemyEmergeProb = 0.35;
        bossEnemyEmergeScore = 180;
        bossBloodRaise=10;
    }

    @Override
    public void harder() {
        enemyBlood+=1;
        enemyBulletPower+=0.1;
        eliteEnemyEmergeProb+=0.01;
        System.out.println("难度提升！敌机血量为："+enemyBlood+" 敌机子弹伤害为："+enemyBulletPower+" 精英机出现概率为："+eliteEnemyEmergeProb);
    }
}
