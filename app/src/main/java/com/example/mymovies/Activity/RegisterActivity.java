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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private EditText userEdt,passEdt,pass2Edt;
    private Button registerBtn;
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tv = findViewById(R.id.loginTxt);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        userEdt = findViewById(R.id.editTextUsername);
        passEdt = findViewById(R.id.editTextPassword);
        pass2Edt = findViewById(R.id.editTextPassword2);
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
    }
    private void handleRegister(){
        if(userEdt.getText().toString().isEmpty() || passEdt.getText().toString().isEmpty() || pass2Edt.getText().toString().isEmpty()){
            Toast.makeText(RegisterActivity.this,"Please fill your username, password, password confirm!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(passEdt.getText().toString().isEmpty() != pass2Edt.getText().toString().isEmpty()){
            Toast.makeText(RegisterActivity.this,"Password confirm is not correct!.",Toast.LENGTH_SHORT).show();
            return;
        }

        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
//        if (db != null) {
//            try {
//                String query = "INSERT INTO Users(username,password) VALUES ('" + userEdt.getText().toString() + "','" + passEdt.getText().toString() + "')";
//                Statement smt = connection.createStatement();
//                ResultSet set = smt.executeQuery(query);
//                while(set.next()){
//                    int mId = set.getInt("userId");
//                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                    intent.putExtra("USERID", mId);
//                    startActivity(intent);
//                    return;
//                }
//                connection.close();
//                Toast.makeText(RegisterActivity.this,"Username or password is not correct!",Toast.LENGTH_SHORT).show();
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
//        }
        if (connection != null) {
            String username = userEdt.getText().toString();
            String password = passEdt.getText().toString();

            // Using a PreparedStatement to avoid SQL injection and return generated keys
            String query = "INSERT INTO Users(username, password) VALUES (?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int userId = generatedKeys.getInt(1);
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("USERID", userId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "User registered successfully, but no userId obtained.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed to register user.", Toast.LENGTH_SHORT).show();
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Toast.makeText(RegisterActivity.this, "An error occurred while registering user.", Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}