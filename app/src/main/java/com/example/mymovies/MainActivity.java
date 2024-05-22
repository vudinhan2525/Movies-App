package com.example.mymovies;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.mymovies.Activity.FavoriteActivity;
import com.example.mymovies.Activity.UserActivity;
import com.example.mymovies.Adapter.ListFilmAdapter;
import com.example.mymovies.Domain.Film;
import com.example.mymovies.Domain.ImageData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterNewMovies, adapterUpComing;
    private RecyclerView recyclerViewNewMovies, recyclerViewUpComing,recyclerViewGenre;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2;
    private ProgressBar loading1, loading2;
    private List<ImageData> imageDataList;
    private List<Film> filmList1;
    private ListFilmAdapter listFilmAdapter;
    Connection connection;

    private EditText editTextSearch;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText searchEditText = findViewById(R.id.editTextText);
        userId = getIntent().getIntExtra("USERID",0);



        ImageView likeBtn = findViewById(R.id.show_liked_films_button);
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    // Handle the Enter key action here
                    String text = searchEditText.getText().toString();
                    Toast.makeText(MainActivity.this, "Entered: " + text, Toast.LENGTH_SHORT).show();
                   // Indicate that the action was handled
                    String query = text;
                    if (!query.isEmpty()) {
                        Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("QUERY", query);
                        startActivity(intent);
                    }

                }
                return false;
            }
        });
        initView();
        sendRequest1();
        sendRequest2();
        sendRequest3("Animation");
        //Genre
        TextView action = findViewById(R.id.genreTxt);
        TextView crime = findViewById(R.id.genreTxt1);
        TextView animation = findViewById(R.id.genreTxt2);
        TextView comedy = findViewById(R.id.genreTxt3);
        TextView adventure = findViewById(R.id.genreTxt4);
        TextView biography = findViewById(R.id.genreTxt5);
        TextView family = findViewById(R.id.genreTxt6);
        TextView drama = findViewById(R.id.genreTxt7);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest3(action.getText().toString());
            }
        });
        crime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest3(crime.getText().toString());
            }
        });
        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest3(animation.getText().toString());
            }
        });
        comedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest3(comedy.getText().toString());
            }
        });
        adventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest3(adventure.getText().toString());
            }
        });
        biography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest3(biography.getText().toString());
            }
        });
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest3(family.getText().toString());
            }
        });
        drama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest3(drama.getText().toString());
            }
        });


        ImageView img = findViewById(R.id.userBtn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }
    private void sendRequest3(String q){
        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        TextView name = findViewById(R.id.textViewLabel);
        if (db != null) {
            try {
                String query = "SELECT * FROM Movies WHERE mGenre LIKE '%" + q + "%'";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(query);
                filmList1 = new ArrayList<>();
                while (set.next()) {
                    int mId = set.getInt("mId");
                    String mImage = set.getString("mImage");
                    String mName = set.getString("mName");
                    int mRating = set.getInt("mRating");
                    String mScore = Integer.toString(mRating);
                    Film f2 = new Film(mId,mName,mScore,mImage,userId);
                    filmList1.add(f2);
                }
                connection.close();
                listFilmAdapter = new ListFilmAdapter(filmList1);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewGenre.setLayoutManager(linearLayoutManager);
                recyclerViewGenre.setAdapter(listFilmAdapter);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private void sendRequest1() {
        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        TextView name = findViewById(R.id.textViewLabel);
        if (db != null) {
            try {
                String query = "SELECT TOP 10 * FROM Movies Order by mRating desc";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(query);
                filmList1 = new ArrayList<>();
                while (set.next()) {
                    int mId = set.getInt("mId");
                    String mImage = set.getString("mImage");
                    String mName = set.getString("mName");
                    int mRating = set.getInt("mRating");
                    String mScore = Integer.toString(mRating);
                    Film f2 = new Film(mId,mName,mScore,mImage,userId);
                    filmList1.add(f2);
                }
                connection.close();
                listFilmAdapter = new ListFilmAdapter(filmList1);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewNewMovies.setLayoutManager(linearLayoutManager);
                recyclerViewNewMovies.setAdapter(listFilmAdapter);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private void sendRequest2() {
        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        TextView name = findViewById(R.id.textViewLabel);
        if (db != null) {
            try {
                String query = "SELECT * FROM Movies Order By mDate desc";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(query);
                filmList1 = new ArrayList<>();
                while (set.next()) {
                    int mId = set.getInt("mId");
                    String mImage = set.getString("mImage");
                    String mName = set.getString("mName");
                    int mRating = set.getInt("mRating");
                    String mScore = Integer.toString(mRating);
                    Film f2 = new Film(mId,mName,mScore,mImage,userId);
                    filmList1.add(f2);
                }
                connection.close();
                listFilmAdapter = new ListFilmAdapter(filmList1);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewUpComing.setLayoutManager(linearLayoutManager);
                recyclerViewUpComing.setAdapter(listFilmAdapter);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private void initView()
    {
        recyclerViewNewMovies = findViewById(R.id.view1);
        recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false ));
        recyclerViewUpComing = findViewById(R.id.view2);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewGenre = findViewById(R.id.viewGenre);
        recyclerViewGenre.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

}