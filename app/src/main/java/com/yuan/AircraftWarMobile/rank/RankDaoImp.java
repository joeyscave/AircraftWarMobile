package com.yuan.AircraftWarMobile.rank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yuan.AircraftWarMobile.entity.User;
import com.yuan.AircraftWarMobile.settings.Settings;
import com.yuan.AircraftWarMobile.utils.JDBCUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RankDaoImp implements RankDao {

    private static final String TAG = "mysql-party-RankDao";
    private List<Record> rank = new LinkedList<>();
    private MyHelper helper;
    private String tableName;

    public RankDaoImp(Context context) {
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

    public boolean insert(Record record) {
        Connection connection = JDBCUtils.getConn();

        try{
            String sql = "insert into "+tableName + "(id,name,score,date) values (?,?,?,?)";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    //将数据插入数据库
                    ps.setInt(1, record.getId());
                    ps.setString(2, record.getName());
                    ps.setInt(3, record.getScore());
                    ps.setString(4, record.getDate());

                    // 执行sql查询语句并返回结果集
                    int rs = ps.executeUpdate();
                    if (rs > 0)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "异常register：" + e.getMessage());
            return false;
        }
    }

    public List<Record> queryAll() throws IOException {
        // 根据数据库名称，建立连接
        Connection connection = JDBCUtils.getConn();
        List<Record> records = new LinkedList<>();

        try {
            String sql = "select * from " + tableName;
            String delsql = "delete from " + tableName;
            PreparedStatement delps = connection.prepareStatement(delsql);
            //delps.executeUpdate(); //是否选择清除数据库
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        //注意：下标是从1开始
                        int id = rs.getInt(1);
                        String name = rs.getString(2);
                        int score = rs.getInt(3);
                        String date = rs.getString(4);
                        records.add(new Record(id, name, score, date));
                    }
                }

                delps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常查询排行数据：" + e.getMessage());
            return null;
        }

        sortRank(records);
        return records;
    }

    public List<Record> sortRank(List<Record> records) throws IOException {
        Collections.sort(records);
        int ranknum = 0;
        for (Record u : records) {
            ranknum += 1;
            u.setId(ranknum);
            insert(u);
            System.out.println("排行" + u.getId() + "\t" + u.getName() + "\t" + u.getScore() + "\t" + u.getDate());
        }
        return records;
    }
}
