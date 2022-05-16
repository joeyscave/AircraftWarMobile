package com.yuan.AircraftWarMobile.aircraft;

import com.yuan.AircraftWarMobile.application.Main;
import com.yuan.AircraftWarMobile.bullet.BaseBullet;
import com.yuan.AircraftWarMobile.bullet.EnemyBullet;
import com.yuan.AircraftWarMobile.factory.prop.AbstractPropFactory;
import com.yuan.AircraftWarMobile.factory.prop.BloodPropFactory;
import com.yuan.AircraftWarMobile.factory.prop.BombPropFactory;
import com.yuan.AircraftWarMobile.factory.prop.BulletPropFactory;
import com.yuan.AircraftWarMobile.prop.AbstractProp;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends MobEnemy {

    /**
     * 攻击方式
     */
    protected int shootNum = 1;     //子弹一次发射数量
    protected int power = Main.settings.enemyBulletPower;       //子弹伤害
    protected int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)


    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;
        BaseBullet baseBullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY*2, power);
            res.add(baseBullet);
        }
        return res;
    }

    @Override
    public AbstractProp generateProp() {
        double ran = Math.random();
        AbstractPropFactory propFactory;
        if (ran < 0.2) {
            propFactory = new BloodPropFactory();
        } else if (ran < 0.4) {
            propFactory = new BombPropFactory();
        } else if (ran < 0.6) {
            propFactory = new BulletPropFactory();
        } else {
            return null;
        }
        return propFactory.create(locationX, locationY);
    }

}
