package com.yuan.AircraftWarMobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yuan.AircraftWarMobile.R;
import com.yuan.AircraftWarMobile.user.UserDao;
import com.yuan.AircraftWarMobile.utils.JDBCUtils;

/**
 * function：连接页面加载首页
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "mysql-party-MainActivity";
    private static Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(runnable).start();
        intent = new Intent(this, SetActivity.class);
    }


    public void reg(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }


    /**
     * function: 登录
     */
    public void login(View view) {

        EditText EditTextAccount = findViewById(R.id.userAccount);
        EditText EditTextPassword = findViewById(R.id.userPassword);

        new Thread() {
            @Override
            public void run() {
                UserDao userDao = new UserDao();
                int msg = userDao.login(EditTextAccount.getText().toString(), EditTextPassword.getText().toString());
                hand1.sendEmptyMessage(msg);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    final Handler hand1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                startActivity(intent);
            } else if (msg.what == 2) {
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
            } else if (msg.what == 3) {
                Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println(JDBCUtils.getConn());
        }
    };
}