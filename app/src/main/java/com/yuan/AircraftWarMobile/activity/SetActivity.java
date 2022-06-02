package com.yuan.AircraftWarMobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.yuan.AircraftWarMobile.R;
import com.yuan.AircraftWarMobile.online.OnlineInfo;
import com.yuan.AircraftWarMobile.service.MusicService;
import com.yuan.AircraftWarMobile.settings.Settings;

public class SetActivity extends AppCompatActivity {

    public static final String TAG = "SetActivity";
    private AlertDialog chooseDialog;
    private AlertDialog matchDialog;
    private Thread matchThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initDialog();
        initThread();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Button button_easy = findViewById(R.id.button_easy);
        Button button_normal = findViewById(R.id.button_normal);
        Button button_hard = findViewById(R.id.button_hard);

        CheckBox checkBox_audio = findViewById(R.id.checkbox_audio);
        button_easy.setOnClickListener((view) -> {
            Settings.backGroundIndex = 0;
            Settings.audio = checkBox_audio.isChecked();
            chooseDialog.show();
        });
        button_normal.setOnClickListener((view) -> {
            Settings.backGroundIndex = 2;
            Settings.audio = checkBox_audio.isChecked();
            chooseDialog.show();
        });
        button_hard.setOnClickListener((view) -> {
            Settings.backGroundIndex = 4;
            Settings.audio = checkBox_audio.isChecked();
            chooseDialog.show();
        });
    }

    private void initDialog() {
        matchDialog = new AlertDialog.Builder(this)
                .setTitle("正在为你匹配对手,请稍候...")//设置标题
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Thread() {
                            @Override
                            public void run() {
                                OnlineInfo.setActiveState(0);
                                matchThread.interrupt();
                            }
                        }.start();
                        Toast.makeText(SetActivity.this, "已取消匹配", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        chooseDialog = new AlertDialog.Builder(this)
                .setTitle("单机 or 对战？")//设置标题
                .setNegativeButton("单机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Settings.opponentName = null;
                        startGameActivity();
                        dialogInterface.dismiss();//销毁对话框
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("对战", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//销毁对话框
                        matchDialog.show();
                        initThread();
                        matchThread.start();
                    }
                }).create();

    }

    private void initThread() {
        matchThread = new Thread() {
            @Override
            public void run() {
                OnlineInfo.setActiveState(1);
                String userName = null;
                while (userName == null && !Thread.currentThread().isInterrupted()) {
                    userName = OnlineInfo.getUserName();
                }
                if (!Thread.currentThread().isInterrupted()) {
                    matchDialog.dismiss();
                    Log.i(TAG, "opponent: " + userName);
                    Settings.opponentName = userName;
                    startGameActivity();
                }
            }
        };
    }

    private void startGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }


}
