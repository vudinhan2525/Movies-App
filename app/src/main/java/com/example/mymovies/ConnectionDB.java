package com.example.mymovies;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
    Connection con;
    @SuppressLint("NewApi")
    public Connection conclass()
    {
        //String IP moi nguoi lay tu IPv4 cua may, port = '1433' la port Default, ai đổi thì tự tìm.
        //Trong sql server mn thêm một login qua sql server, kích hoạt security cho đăng nhập bằng sql
        //đặt username và password.
        //Khoa
        String ip = "192.168.10.53", port = "1433", db = "MoviesApp", username = "KHOA", password = "147852369";
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String connectURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + db + ";user=" + username + ";"+"password=" + password + ";";
            Log.i("id",connectURL);
            con = DriverManager.getConnection(connectURL);
        } catch (Exception e) {
            Log.i("Error", e.getMessage());
        }
        return con;
    }


}
