package com.example.mymovies.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mymovies.Adapter.ListFilmAdapter;
import com.example.mymovies.ConnectionDB;
import com.example.mymovies.Domain.Film;
import com.example.mymovies.MainActivity;
import com.example.mymovies.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private EditText userEdt,passEdt;
    private Button loginBtn;
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        TextView tv = findViewById(R.id.registerText);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView(){
        userEdt = findViewById(R.id.editTextUsername);
        passEdt = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userEdt.getText().toString().isEmpty() || passEdt.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please fill your username and password!",Toast.LENGTH_SHORT).show();
                }
                else {
                    checkLogin();
                }
            }
        });
    }
    private void checkLogin(){

        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        if (db != null) {
            try {
                String query = "SELECT * FROM Users WHERE username = '" + userEdt.getText().toString() + "' AND password = '" + passEdt.getText().toString() + "'";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(query);
                while (set.next()) {
                    int mId = set.getInt("userId");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("USERID", mId);
                    startActivity(intent);
                    return;
                }
                connection.close();
                Toast.makeText(LoginActivity.this,"Username or password is not correct!",Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

    }


}