package com.yuan.AircraftWarMobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.yuan.AircraftWarMobile.application.Main;
import com.yuan.AircraftWarMobile.online.OnlineInfo;
import com.yuan.AircraftWarMobile.service.MusicService;
import com.yuan.AircraftWarMobile.settings.Settings;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";
    private GameSurfaceView mGameSurfaceView;
    public Intent intent_musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Main.init();
        playMusic("bgm");
        mGameSurfaceView = new GameSurfaceView(this);
        setContentView(mGameSurfaceView);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void playMusic(String type) {
        if (Settings.audio) {
            intent_musicService = new Intent(this, MusicService.class);
            intent_musicService.putExtra("type", type);
            startService(intent_musicService);
        }
    }

    public void gameOver() {
        if (Settings.audio) {
            stopService(intent_musicService);
        }

        // 重置在线状态
        new Thread() {
            @Override
            public void run() {
                OnlineInfo.setActiveState(0);
            }
        }.start();

        // 向远端更新死亡状态，拉取对手死亡状态
        new Thread() {
            @Override
            public void run() {
                OnlineInfo.updateDeath(1);
                synchronized (Main.Death_LOCK) {
                    Settings.opponentDeath = OnlineInfo.getDeath();
                }
            }
        }.start();

        Intent intent = new Intent(this, RankActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        if (Settings.audio) {
            stopService(intent_musicService);
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return true;
    }
}