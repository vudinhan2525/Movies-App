package com.example.mymovies;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymovies.Adapter.FilmListAdapter;
import com.example.mymovies.Adapter.ImageListAdapter;
import com.example.mymovies.Domain.ImageData;
import com.google.gson.Gson;

import com.example.mymovies.Domain.ListFilm;

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

    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        sendRequest1();
        //sendRequest2();
    }

    private void sendRequest1() {
//        mRequestQueue = Volley.newRequestQueue(this);
//        loading1.setVisibility(View.VISIBLE);
//        mStringRequest = new StringRequest(Request.Method.GET,"https://moviesapi.ir/api/v1/movies?page=1", response -> {
//            Gson gson = new Gson();
//            loading1.setVisibility(View.GONE);
//            ListFilm items = gson.fromJson(response, ListFilm.class);
//            adapterNewMovies = new FilmListAdapter(items);
//            recyclerViewNewMovies.setAdapter(adapterNewMovies);
//        }, error -> {
//            Log.i("MyMovies", "sendRequest1: " + error.toString());
//            loading1.setVisibility(View.GONE);
//        } );
//        mRequestQueue.add(mStringRequest);
        imageDataList = new ArrayList<>();

        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        TextView name = findViewById(R.id.textViewLabel);

        if (db != null) {
            try {
                String query = "SELECT mImage FROM Movies";
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(query);
                while (set.next()) {
                    String imagePath = set.getString("mImage");
                    imageDataList.add(new ImageData(imagePath));
                }
                connection.close();
                adapterNewMovies = new ImageListAdapter(imageDataList);
                recyclerViewNewMovies.setAdapter(adapterNewMovies);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    /*private void sendRequest2() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2 = new StringRequest(Request.Method.GET,"https://moviesapi.ir/api/v1/movies?page=2", response -> {
            Gson gson = new Gson();
            loading2.setVisibility(View.GONE);
            ListFilm items = gson.fromJson(response, ListFilm.class);
            adapterUpComing = new FilmListAdapter(items);
            recyclerViewUpComing.setAdapter(adapterUpComing);
        }, error -> {
            loading2.setVisibility(View.GONE);
        } );
        mRequestQueue.add(mStringRequest2);
    }*/

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