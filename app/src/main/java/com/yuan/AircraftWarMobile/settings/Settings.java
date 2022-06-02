package com.yuan.AircraftWarMobile.settings;

public abstract class Settings {
    static public boolean audio;
    static public int death;
    static public int opponentDeath;
    static public int backGroundIndex;
    static public String nickname;
    static public String opponentName;
    static public int opponentScore;
    static public String userAccount;
    static public int score;
    public int enemyBlood;
    public int enemySpeed;
    public int enemyBulletPower;
    public int heroBulletPower;
    public double eliteEnemyEmergeProb;
    public int bossEnemyEmergeScore;
    public int bossBloodRaise;

    public void setGame() {
        score = 0;
        death = 0;
        opponentScore = 0;
        opponentDeath = 0;
        set();
    }

    public abstract void set();

    public abstract void harder();

}
