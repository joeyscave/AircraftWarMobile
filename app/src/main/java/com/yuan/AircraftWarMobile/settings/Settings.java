package com.yuan.AircraftWarMobile.settings;

public abstract class Settings {
    static public boolean audio;
    static public int backGroundIndex;
    public int score;
    public int enemyBlood;
    public int enemySpeed;
    public int enemyBulletPower;
    public int heroBulletPower;
    public double eliteEnemyEmergeProb;
    public int bossEnemyEmergeScore;
    public int bossBloodRaise;

    public void setGame() {
        score = 0;
        set();
    }

    public abstract void set();

    public abstract void harder();

}
