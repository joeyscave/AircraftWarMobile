package com.yuan.AircraftWarMobile.aircraft;

import com.yuan.AircraftWarMobile.application.Main;
import com.yuan.AircraftWarMobile.bullet.BaseBullet;
import com.yuan.AircraftWarMobile.observer.Subscriber;
import com.yuan.AircraftWarMobile.prop.AbstractProp;

import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft implements Subscriber {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return null;
    }

    @Override
    public AbstractProp generateProp() {
        return null;
    }

    @Override
    public void update() {
        this.vanish();
        Main.settings.score += 10;
    }
}
