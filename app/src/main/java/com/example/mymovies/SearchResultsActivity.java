package com.example.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.Adapter.ListFilmAdapter;
import com.example.mymovies.Domain.Film;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSearchResults;
    private ListFilmAdapter listFilmAdapter;
    private List<Film> searchResultsList;
    private Connection connection;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        GridLayoutManager gridFind = new GridLayoutManager(this, 3);
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        recyclerViewSearchResults.setLayoutManager(gridFind);

        String query = getIntent().getStringExtra("QUERY");
        TextView text = findViewById(R.id.resultTxt);
        text.setText("Result for: \"" + query + "\"");
        userId = getIntent().getIntExtra("userId",0);
        fetchSearchResults(query);
        ImageButton btn = findViewById(R.id.backBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultsActivity.this, MainActivity.class);
                intent.putExtra("USERID", userId);

                startActivity(intent);
            }
        });
    }

    private void fetchSearchResults(String query) {
        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        if (db != null) {
            try {
                String sqlQuery = "SELECT * FROM Movies WHERE mName LIKE '%" + query + "%'";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(sqlQuery);
                searchResultsList = new ArrayList<>();
                while (set.next()) {
                    int mId = set.getInt("mId");
                    String mImage = set.getString("mImage");
                    String mName = set.getString("mName");
                    int mRating = set.getInt("mRating");
                    String mScore = Integer.toString(mRating);
                    Film film = new Film(mId, mName, mScore, mImage,userId);
                    searchResultsList.add(film);
                }
                connection.close();
                listFilmAdapter = new ListFilmAdapter(searchResultsList);
                recyclerViewSearchResults.setAdapter(listFilmAdapter);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
