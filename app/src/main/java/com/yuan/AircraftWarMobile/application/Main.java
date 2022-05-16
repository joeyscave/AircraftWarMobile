package com.yuan.AircraftWarMobile.application;

import android.util.Log;

import com.yuan.AircraftWarMobile.settings.Settings;
import com.yuan.AircraftWarMobile.settings.SettingsEasy;
import com.yuan.AircraftWarMobile.settings.SettingsHard;
import com.yuan.AircraftWarMobile.settings.SettingsNormal;

/**
 * 程序入口
 *
 * @author hitsz
 */
public class Main {

    public static int WINDOW_WIDTH = 512;
    public static int WINDOW_HEIGHT = 768;
    public static final Object Main_LOCK = new Object();
    public static final Object Bullet_LOCK = new Object();
    public static Settings settings;

    public static void init() {

        Log.i("---pipeline---", "----Game Start----");

        // 初始化游戏
        switch (Settings.backGroundIndex / 2) {
            case 0:
                settings = new SettingsEasy();
                break;
            case 1:
                settings = new SettingsNormal();
                break;
            case 2:
                settings = new SettingsHard();
                break;
            default:
        }
        settings.setGame();

    }
}
