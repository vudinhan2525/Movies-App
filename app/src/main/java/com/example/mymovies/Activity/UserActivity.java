package com.example.mymovies.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.mymovies.Adapter.ListFilmAdapter;
import com.example.mymovies.ConnectionDB;
import com.example.mymovies.Domain.Film;
import com.example.mymovies.MainActivity;
import com.example.mymovies.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private int userId;
    Connection connection;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userlayout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView imageView = findViewById(R.id.mainImage);
        userId = getIntent().getIntExtra("userId",0);
        Glide.with(this)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8QpKHeBbrELrNRa-63gDAsBM2TQR3GzSxCYwMw73LVw&s")
                .transform(new CircleCrop())
                .into(imageView);
        ImageButton btn = findViewById(R.id.backBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                intent.putExtra("USERID", userId);
                startActivity(intent);
            }
        });
        name = findViewById(R.id.textView2);
        getUserInfo();
        Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getUserInfo(){

        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        if (db != null) {
            try {
                String query = "SELECT * FROM Users WHERE userId = " + userId;
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(query);
                while (set.next()) {
                    name.setText(set.getString("username"));
                }
                connection.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

}