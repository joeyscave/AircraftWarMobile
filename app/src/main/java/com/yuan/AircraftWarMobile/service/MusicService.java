package com.yuan.AircraftWarMobile.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.yuan.AircraftWarMobile.R;

public class MusicService extends Service {
    private static final String TAG = "MusicService";

    public MusicService() {
    }

    // 创建播放器对象
    private MediaPlayer player_bgm;
    private MediaPlayer player_bgm_boss;
    private MediaPlayer player_bomb_explosion;
    private MediaPlayer player_bullet;
    private MediaPlayer player_bullet_hit;
    private MediaPlayer player_game_over;
    private MediaPlayer player_get_supply;

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "==== MusicService onStartCommand ===");
        String type = intent.getStringExtra("type");
        playMusic(type);
        return super.onStartCommand(intent, flags, startId);
    }

    //播放音乐
    public void playMusic(String type) {
        switch (type) {
            case "bgm":
                player_bgm.start();
                break;
            case "bgm_boss":
                player_bgm_boss.start();
                break;
            case "bomb_explosion":
                player_bomb_explosion.start();
                break;
            case "bullet":
                player_bullet.start();
                break;
            case "bullet_hit":
                player_bullet_hit.start();
                break;
            case "get_supply":
                player_get_supply.start();
                break;
            case "game_over":
                player_game_over.start();
                break;
            case "stop_bgm_boss":
                stop_bgm_boss();
                break;
        }
    }

    /**
     * 停止播放
     */
    public void stop_bgm_boss() {
        player_bgm_boss.reset();
        player_bgm_boss = MediaPlayer.create(this, R.raw.bgm_boss);
    }

    public void stopMusic(){
        player_bgm.stop();
        player_bgm.reset();
        player_bgm.release();
        player_bgm=null;
        player_bgm_boss.stop();
        player_bgm_boss.reset();
        player_bgm_boss.release();
        player_bgm_boss=null;
        player_bomb_explosion.stop();
        player_bomb_explosion.reset();
        player_bomb_explosion.release();
        player_bomb_explosion=null;
        player_bullet.stop();
        player_bullet.reset();
        player_bullet.release();
        player_bullet=null;
        player_bullet_hit.stop();
        player_bullet_hit.reset();
        player_bullet_hit.release();
        player_bullet_hit=null;
        player_game_over.stop();
        player_game_over.reset();
        player_game_over.release();
        player_game_over=null;
        player_get_supply.stop();
        player_get_supply.reset();
        player_get_supply.release();
        player_get_supply=null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "==== MusicService onCreate ===");
        player_bgm = MediaPlayer.create(this, R.raw.bgm);
        player_bgm_boss = MediaPlayer.create(this, R.raw.bgm_boss);
        player_bullet = MediaPlayer.create(this, R.raw.bullet);
        player_bullet_hit = MediaPlayer.create(this, R.raw.bullet_hit);
        player_get_supply = MediaPlayer.create(this, R.raw.get_supply);
        player_bomb_explosion = MediaPlayer.create(this, R.raw.bomb_explosion);
        player_game_over = MediaPlayer.create(this, R.raw.game_over);

        player_bgm.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                player_bgm.start();
                player_bgm.setLooping(true);
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }
}