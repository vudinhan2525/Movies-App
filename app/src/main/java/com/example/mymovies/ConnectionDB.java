package com.example.mymovies;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    Connection con;
    private static final String TAG = "ConnectionDB";
    private static final String IP = "sql12.freemysqlhosting.net";
    private static final String PORT = "3306";
    private static final String DATABASE = "sql12708528";
    private static final String USERNAME = "sql12708528";
    private static final String PASSWORD = "9yUdfFqkyH";

    // @SuppressLint("NewApi")
//    public Connection conclass()
//    {
//        //String IP moi nguoi lay tu IPv4 cua may, port = '1433' la port Default, ai đổi thì tự tìm.
//        //Trong sql server mn thêm một login qua sql server, kích hoạt security cho đăng nhập bằng sql
//        //đặt username và password.
//        //Khoa
////        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
////        StrictMode.setThreadPolicy(a);
////        String connectURL = null;
////
////        try {
////            Class.forName("net.sourceforge.jtds.jdbc.Driver");
////            connectURL = "jdbc:jtds:sqlserver://192.168.3.101:1433;databaseName=MoviesApp;user=KHOA;password=147852369;";
////
////            con = DriverManager.getConnection(connectURL);
////            Log.i("id",connectURL);
////            //String connectionUrl = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD;
////            //con = DriverManager.getConnection(connectionUrl);
////
////            } catch (ClassNotFoundException e) {
////                Log.e(TAG, "JDBC Driver not found.", e);
////            } catch (SQLException e) {
////                Log.e(TAG, "SQL Error: " + e.getMessage(), e);
////            } catch (Exception e) {
////                Log.e(TAG, "Unexpected Error: " + e.getMessage(), e);
////            }
////        return con;
////            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
////            StrictMode.setThreadPolicy(policy);
////            try {
////                String connectURL = "jdbc:jtds:sqlserver://192.168.3.100:1433;databaseName=MoviesApp;user=KHOA;password=147852369;";
////                Class.forName("net.sourceforge.jtds.jdbc.Driver");
////                con = DriverManager.getConnection(connectURL);
////                Log.i(TAG, "Connection established successfully: " + connectURL);
////            } catch (ClassNotFoundException e) {ter
////                Log.e(TAG, "JDBC Driver not found.", e);
////            } catch (SQLException e) {
////                Log.e(TAG, "SQL Error: " + e.getMessage(), e);
////            } catch (Exception e) {
////                Log.e(TAG, "Unexpected Error: " + e.getMessage(), e);
////            }
//        return con;
//}
    @SuppressLint("NewApi")
    public Connection conclass() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String connectionUrl = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl);
            Log.i(TAG, "Connection established successfully.");
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "JDBC Driver not found.", e);
        } catch (SQLException e) {
            Log.e(TAG, "SQL Error: " + e.getMessage(), e);
        } catch (Exception e) {
            Log.e(TAG, "Unexpected Error: " + e.getMessage(), e);
        }

        return connection;
    }
    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                String connectURL = "jdbc:jtds:sqlserver://192.168.3.101:1433;databaseName=MoviesApp;user=KHOA;password=147852369;";
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                con = DriverManager.getConnection(connectURL);
                Log.i(TAG, "Connection established successfully: " + connectURL);
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "JDBC Driver not found.", e);
            } catch (SQLException e) {
                Log.e(TAG, "SQL Error: " + e.getMessage(), e);
            } catch (Exception e) {
                Log.e(TAG, "Unexpected Error: " + e.getMessage(), e);
            }
        }
    });

}
