package com.yuan.AircraftWarMobile.aircraft;

import com.yuan.AircraftWarMobile.service.ImageManager;
import com.yuan.AircraftWarMobile.application.Main;
import com.yuan.AircraftWarMobile.bullet.BaseBullet;
import com.yuan.AircraftWarMobile.bullet.HeroBullet;
import com.yuan.AircraftWarMobile.prop.AbstractProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**
     * 攻击方式
     */
    private int shootNum = 2;     //子弹原始发射数量
    private int shootNumIncrease = 0; //子弹增益数量
    private int power = Main.settings.heroBulletPower;       //子弹伤害
    private int direction = -1;  //子弹射击方向 (向上发射：1，向下发射：-1)
    private boolean scattering = false;

    /**
     * 单例模式生成的唯一实例
     */
    private volatile static HeroAircraft heroAircraft;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public static HeroAircraft getHeroAircraft() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0,
                            0,
                            100);
                }
            }
        }
        return heroAircraft;
    }

    public static void clearHeroAircraft() {
        heroAircraft = null;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    public AbstractProp generateProp() {
        return null;
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;
        BaseBullet baseBullet;
        for (int i = 0; i < shootNum + shootNumIncrease; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (scattering) {
                speedX = (i - 1) * 2;
            }
            baseBullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY*3, power);
            res.add(baseBullet);
        }
        return res;
    }

    public void setIncreaseShootNum(int increaseBullet) {
        shootNumIncrease = increaseBullet;
    }

    public void reverseScattering() {
        scattering = !scattering;
    }
}
