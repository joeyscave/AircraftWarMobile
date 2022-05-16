package com.yuan.AircraftWarMobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yuan.AircraftWarMobile.R;

public class RankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}