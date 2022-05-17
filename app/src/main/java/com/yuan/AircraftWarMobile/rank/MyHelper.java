package com.yuan.AircraftWarMobile.rank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yuan.AircraftWarMobile.settings.Settings;

import java.sql.SQLException;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context){
        super(context,"itcast.db",null,3);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE easyRank(id INTEGER, name VARCHAR(20), score INTEGER, date VARCHAR(20))");
        Log.i("MyHelper", "==== Easy SQLite onCreateCommand ===");
        db.execSQL("CREATE TABLE normalRank(id INTEGER, name VARCHAR(20), score INTEGER, date VARCHAR(20))");
        Log.i("MyHelper", "==== Normal SQLite onCreateCommand ===");
        db.execSQL("CREATE TABLE hardRank(id INTEGER, name VARCHAR(20), score INTEGER, date VARCHAR(20))");
        Log.i("MyHelper", "==== Hard SQLite onCreateCommand ===");
    }
    public  void  onUpgrade(SQLiteDatabase db,int oldVersion,int newViersion){
        System.out.println("onUpgrade");
        db.execSQL("drop table if exists "+"easyRank");
        db.execSQL("drop table if exists "+"normalRank");
        db.execSQL("drop table if exists "+"hardRank");
        onCreate(db);
    }
}
