package com.example.mymovies.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.Adapter.ListFilmAdapter;
import com.example.mymovies.ConnectionDB;
import com.example.mymovies.Domain.Film;
import com.example.mymovies.MainActivity;
import com.example.mymovies.R;
import com.example.mymovies.SearchResultsActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerLikedResults;
    private ListFilmAdapter listFilmAdapter;
    private List<Film> searchResultsList;
    private Connection connection;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        GridLayoutManager gridFind = new GridLayoutManager(this, 3);
        recyclerLikedResults = findViewById(R.id.recyclerLikedResult);
        recyclerLikedResults.setLayoutManager(gridFind);
        userId = getIntent().getIntExtra("userId",0);
        fetchSearchResults();
        ImageButton btn = findViewById(R.id.backBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
                intent.putExtra("USERID", userId);
                startActivity(intent);
            }
        });
    }

    private void fetchSearchResults() {

        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        if (db != null) {
            try {
                searchResultsList = new ArrayList<>();
                String sqlQuery = "SELECT filmId FROM UserLike WHERE userId = " + userId;
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(sqlQuery);

                while(set.next()){
                    int r = set.getInt("filmId");
                    String newQuery = "SELECT * FROM Movies WHERE mId = " + r;

                    Statement newsmt = connection.createStatement();
                    ResultSet newset = newsmt.executeQuery(newQuery);
                    while (newset.next()){

                        int mId = newset.getInt("mId");

                        String mImage = newset.getString("mImage");

                        String mName = newset.getString("mName");

                        int mRating = newset.getInt("mRating");

                        String mScore = Integer.toString(mRating);
                        Film film = new Film(mId, mName, mScore, mImage,userId,true);

                        searchResultsList.add(film);
                    }
                }

                connection.close();
                listFilmAdapter = new ListFilmAdapter(searchResultsList);
                recyclerLikedResults.setAdapter(listFilmAdapter);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
