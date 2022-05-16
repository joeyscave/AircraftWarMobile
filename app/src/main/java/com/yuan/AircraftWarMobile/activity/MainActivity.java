package com.yuan.AircraftWarMobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;

import com.yuan.AircraftWarMobile.R;
import com.yuan.AircraftWarMobile.service.MusicService;
import com.yuan.AircraftWarMobile.settings.Settings;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Button button_easy = findViewById(R.id.button_easy);
        Button button_normal = findViewById(R.id.button_normal);
        Button button_hard = findViewById(R.id.button_hard);
//        Button button_music = findViewById(R.id.button_music);
        CheckBox checkBox_audio = findViewById(R.id.checkbox_audio);
        Intent intent = new Intent(this, GameActivity.class);
        button_easy.setOnClickListener((view) -> {
            Settings.backGroundIndex = 0;
            Settings.audio = checkBox_audio.isChecked();
            startActivity(intent);
        });
        button_normal.setOnClickListener((view) -> {
            Settings.backGroundIndex = 2;
            Settings.audio = checkBox_audio.isChecked();
            startActivity(intent);
        });
        button_hard.setOnClickListener((view) -> {
            Settings.backGroundIndex = 4;
            Settings.audio = checkBox_audio.isChecked();
            startActivity(intent);
        });
//        // 音效测试
//        button_music.setOnClickListener((view) -> {
//            Intent intent1 = new Intent(this, MusicService.class);
//            intent1.putExtra("action", "play");
//            startService(intent1);
//        });
    }
}