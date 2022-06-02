package com.yuan.AircraftWarMobile.online;

import android.util.Log;

import com.yuan.AircraftWarMobile.settings.Settings;
import com.yuan.AircraftWarMobile.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OnlineInfo {

    private static final String TAG = "onlineInfo";

    public static String getUserName() {
        Connection connection = JDBCUtils.getConn();
        String userName = null;
        try {
            String sql = "select userName from user where userState = 1 and userAccount!=?";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, Settings.userAccount);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        //注意：下标是从1开始
                        userName = rs.getString(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常getUserName：" + e.getMessage());
            return null;
        }
        return userName;
    }

    public static int getScore() {
        Connection connection = JDBCUtils.getConn();
        int userScore = -1;
        try {
            String sql = "select userScore from user where userName = ?";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, Settings.opponentName);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        //注意：下标是从1开始
                        userScore = rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常getScore：" + e.getMessage());
            return -1;
        }
        Settings.opponentScore = userScore;
        return userScore;
    }

    public static boolean sentScore() {
        Connection connection = JDBCUtils.getConn();

        try {
            String sql = "update user set userScore=? where userAccount=?";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {

                    //将数据插入数据库
                    ps.setInt(1, Settings.score);
                    ps.setString(2, Settings.userAccount);

                    // 执行sql查询语句并返回结果集
                    int rs = ps.executeUpdate();
                    if (rs >= 0)
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
            Log.e(TAG, "异常sentScore：" + e.getMessage());
            return false;
        }
    }

    public static boolean setActiveState(int state) {
        Connection connection = JDBCUtils.getConn();

        try {
            String sql = "update user set userState=? where userAccount=?";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {

                    Log.i(TAG, "setActiveState: " + Settings.userAccount);
                    // 将数据插入数据库
                    ps.setInt(1, state);
                    ps.setString(2, Settings.userAccount);

                    // 执行sql查询语句并返回结果集
                    int rs = ps.executeUpdate();
                    if (rs >= 0) {
                        return true;
                    } else
                        return false;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "异常setActiveState：" + e.getMessage());
            return false;
        }
    }

    public static boolean updateDeath(int death) {
        Connection connection = JDBCUtils.getConn();

        try {
            String sql = "update user set userDel = ? where userAccount=?";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {

                    //将数据插入数据库
                    ps.setInt(1, death);
                    ps.setString(2, Settings.userAccount);

                    // 执行sql查询语句并返回结果集
                    int rs = ps.executeUpdate();
                    if (rs >= 0)
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
            Log.e(TAG, "异常updateDeath：" + e.getMessage());
            return false;
        }
    }

    public static int getDeath() {
        Connection connection = JDBCUtils.getConn();
        int userScore = -1;
        try {
            String sql = "select userDel from user where userName = ?";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, Settings.opponentName);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        //注意：下标是从1开始
                        userScore = rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常getScore：" + e.getMessage());
            return -1;
        }
        Settings.opponentScore = userScore;
        return userScore;
    }
}
