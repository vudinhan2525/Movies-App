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
        String ip = "192.168.9.94", port = "1433", db = "MoviesApp", username = "KHOALAP", password = "147852369";
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String connectURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + db + ";user=" + username + ";"+"password=" + password + ";";
            con = DriverManager.getConnection(connectURL);
        } catch (Exception e) {
            Log.i("Error", e.getMessage());
        }
        return con;
    }


}
