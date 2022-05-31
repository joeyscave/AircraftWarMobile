package com.yuan.AircraftWarMobile.aircraft;

import com.yuan.AircraftWarMobile.activity.GameSurfaceView;
import com.yuan.AircraftWarMobile.service.ImageManager;
import com.yuan.AircraftWarMobile.application.Main;
import com.yuan.AircraftWarMobile.bullet.BaseBullet;
import com.yuan.AircraftWarMobile.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends EliteEnemy {

    private int shootNum = 3;

    private static boolean exist = false;

    private BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedY = this.getSpeedY() + direction * 5;
        BaseBullet baseBullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            int speedX = (i - 1) * 2;
            baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY * 2, power);
            res.add(baseBullet);
        }
        return res;
    }

    @Override
    public void vanish() {
        super.vanish();
        BossEnemy.exist = false;
        GameSurfaceView.bgm_boss = false;
        this.hp += Main.settings.bossBloodRaise;
    }

    /**
     * 类似单例模式返回boss机实例，确保同时只有一个实例
     *
     * @return boss机唯一实例
     */
    public static BossEnemy getInstance() {
        if (exist) return null;
        BossEnemy.exist = true;
        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                10,
                0,
                400);
    }

    /**
     * 空实现
     * 炸弹不对Boss机生效
     */
    @Override
    public void update() {
        return;
    }
}
