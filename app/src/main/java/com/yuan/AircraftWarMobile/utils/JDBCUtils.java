package com.yuan.AircraftWarMobile.utils;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * function： 数据库工具类，连接数据库用
 */
public class JDBCUtils {
    private static final String TAG = "mysql-party-JDBCUtils";

    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动

    private static String dbName = "party";// 数据库名称

    private static String user = "root";// 用户名

    private static String password = "yl2vXBS%FjGoSfby";// 密码
    private static Connection connection = null;
    static {
        try {
            Class.forName(driver);// 动态加载类
            String ip = "120.76.117.99";// 模拟机调试写成本机地址，手机调试写成wifi的ipv4地址，不能写成localhost，同时手机和电脑连接的网络必须是同一个

            long start,end;
            start = System.currentTimeMillis();
            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName,
                    user, password);
            end = System.currentTimeMillis();
            Log.i(TAG, "运行时间：" + (end - start) + "(ms)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        return connection;
    }
}
