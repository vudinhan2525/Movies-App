package com.example.mymovies;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymovies.Adapter.FilmListAdapter;
import com.example.mymovies.Adapter.ListFilmAdapter;
import com.example.mymovies.Domain.Film;
import com.example.mymovies.Domain.ImageData;
import com.example.mymovies.Domain.ListFilm;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterNewMovies, adapterUpComing;
    private RecyclerView recyclerViewNewMovies, recyclerViewUpComing;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2;
    private ProgressBar loading1, loading2;
    private List<ImageData> imageDataList;
    private List<Film> filmList1;
    private ListFilmAdapter listFilmAdapter;
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        sendRequest1();
        sendRequest2();
    }

    private void sendRequest1() {
        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        TextView name = findViewById(R.id.textViewLabel);
        if (db != null) {
            try {
                String query = "SELECT * FROM Movies";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(query);
                filmList1 = new ArrayList<>();
                while (set.next()) {
                    int mId = set.getInt("mId");
                    String mImage = set.getString("mImage");
                    String mName = set.getString("mName");
                    int mRating = set.getInt("mRating");
                    String mScore = Integer.toString(mRating);
                    Film f2 = new Film(mId,mName,mScore,mImage);
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
                String query = "SELECT * FROM Movies";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(query);
                filmList1 = new ArrayList<>();
                while (set.next()) {
                    int mId = set.getInt("mId");
                    String mImage = set.getString("mImage");
                    String mName = set.getString("mName");
                    int mRating = set.getInt("mRating");
                    String mScore = Integer.toString(mRating);
                    Film f2 = new Film(mId,mName,mScore,mImage);
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
        loading1 = findViewById(R.id.loading1);
        loading2 = findViewById(R.id.loading2);

    }
}