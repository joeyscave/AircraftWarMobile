package com.yuan.AircraftWarMobile.service;

import android.graphics.Bitmap;

import com.yuan.AircraftWarMobile.aircraft.BossEnemy;
import com.yuan.AircraftWarMobile.aircraft.EliteEnemy;
import com.yuan.AircraftWarMobile.aircraft.HeroAircraft;
import com.yuan.AircraftWarMobile.aircraft.MobEnemy;
import com.yuan.AircraftWarMobile.bullet.EnemyBullet;
import com.yuan.AircraftWarMobile.bullet.HeroBullet;
import com.yuan.AircraftWarMobile.prop.BloodProp;
import com.yuan.AircraftWarMobile.prop.BombProp;
import com.yuan.AircraftWarMobile.prop.BulletProp;

import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static Bitmap BACKGROUND_IMAGE;
    public static Bitmap BACKGROUND_IMAGE_EASY;
    public static Bitmap BACKGROUND_IMAGE_NORMAL;
    public static Bitmap BACKGROUND_IMAGE_HARD;
    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_ENEMY_IMAGE;
    public static Bitmap BLOOD_PROP_IMAGE;
    public static Bitmap BOMB_PROP_IMAGE;
    public static Bitmap BULLET_PROP_IMAGE;

    public static void putMap() {
        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BloodProp.class.getName(), BLOOD_PROP_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BombProp.class.getName(), BOMB_PROP_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BulletProp.class.getName(), BULLET_PROP_IMAGE);
    }

    public static Bitmap get(String className) {
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj) {
        if (obj == null) {
            return null;
        }
        return get(obj.getClass().getName());
    }

}
