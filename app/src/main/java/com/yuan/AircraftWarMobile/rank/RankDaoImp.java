package com.yuan.AircraftWarMobile.rank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yuan.AircraftWarMobile.settings.Settings;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RankDaoImp implements RankDao {

    private List<Record> rank = new LinkedList<>();
    private MyHelper helper;
    private String tableName;

    public RankDaoImp(Context context) {
        helper = new MyHelper(context);
        switch (Settings.backGroundIndex / 2) {
            case 0:
                tableName = "easyRank";
                break;
            case 1:
                tableName = "normalRank";
                break;
            case 2:
                tableName = "hardRank";
                break;
            default:
        }
    }

    public void insert(Record record) {
        SQLiteDatabase db = helper.getWritableDatabase();
        //db.execSQL("delete from " + tableName);//通过该指令清空数据库
        ContentValues values = new ContentValues();
        values.put("id",record.getId());
        values.put("name", record.getName());
        values.put("score", record.getScore());
        values.put("date", record.getDate());
        db.insert(tableName, null, values);
        db.close();
    }

    public List<Record> queryAll() throws IOException {
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor c=db.query(tableName,null,null,null,null,null,"score DESC");
        List<Record> records = new LinkedList<>();
        while (c.moveToNext()){
            int id=c.getInt(c.getColumnIndex("id"));
            String name=c.getString(c.getColumnIndex("name"));
            int score=c.getInt(c.getColumnIndex("score"));
            String date=c.getString(c.getColumnIndex("date"));
            records.add(new Record(id ,name, score, date));
        }
        db.execSQL("delete from " + tableName);
        sortRank(records);
        c.close();
        db.close();
        return records;
    }

    public List<Record> sortRank (List<Record> records) throws IOException{
        Collections.sort(records);
        int ranknum = 0;
        for(Record u:records){
            ranknum += 1;
            u.setId(ranknum);
            insert(u);
            System.out.println("排行" + u.getId()+"\t"+u.getName()+"\t"+u.getScore()+"\t"+u.getDate());
        }
        return records;
    }
}
