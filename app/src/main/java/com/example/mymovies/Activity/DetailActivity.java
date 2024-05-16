package com.example.mymovies.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mymovies.Adapter.ImageListAdapter;
import com.example.mymovies.Adapter.ListFilmAdapter;
import com.example.mymovies.ConnectionDB;
import com.example.mymovies.Domain.Film;
import com.example.mymovies.Domain.FilmItem;
import com.example.mymovies.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, movieTimeTxt, movieDateTxt, movieSummaryInfo, movieActorsInfo, genreTxt;
    private NestedScrollView scrollView;
    private int idFilm;
    private ShapeableImageView pic1;
    private ImageView pic2, backImg, pic3;
    private RecyclerView.Adapter adapterImgList, imagesRecyclerView;
    private RecyclerView recyclerView;

   // private List<Film> filmList1;
   // private ListFilmAdapter listFilmAdapter;
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        idFilm = getIntent().getIntExtra("id", 0);
        initView();
        Log.i("id", String.valueOf(idFilm));

        sendRequest();
    }


    private void sendRequest() {
//          "SELECT * FROM MOVIES WHERE mId = 0"
//        mRequestQueue = Volley.newRequestQueue(this);
//        progressBar.setVisibility(View.VISIBLE);
//        scrollView.setVisibility(View.GONE);
//        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idFilm, response -> {
//            Gson gson = new Gson();
//            progressBar.setVisibility(View.GONE);
//            scrollView.setVisibility(View.VISIBLE);
//            FilmItem items = gson.fromJson(response,FilmItem.class);
//            Glide.with(DetailActivity.this).load(items.getPoster()).into(pic1);
//            Glide.with(DetailActivity.this).load(items.getPoster()).into(pic2);
//            titleTxt.setText(items.getTitle());
//            movieRateTxt.setText(items.getRated());
//            movieTimeTxt.setText(items.getRuntime());
//            movieDateTxt.setText(items.getReleased());
//            movieSummaryInfo.setText(items.getPlot());
//            movieActorsInfo.setText(items.getActors());
//            if(items.getImages() != null)
//            {
//                adapterImgList = new ImageListAdapter(items.getImages());
//                recyclerView.setAdapter(adapterImgList);
//            }
//        }, error -> {
//            progressBar.setVisibility(View.GONE);
//            Log.i("uilover", "onErrorResponse" + error.toString());
//        });
//        mRequestQueue.add(mStringRequest);
        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        TextView name = findViewById(R.id.textViewLabel);
        if (db != null) {
            try {
                String query = "SELECT * FROM Movies WHERE mId =" + idFilm;
                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(query);
                while (set.next()) {
                    Glide.with(DetailActivity.this).load(set.getString("mImage")).into(pic1);
                    Glide.with(DetailActivity.this).load(set.getString("mImage1")).into(pic2);
                    Glide.with(DetailActivity.this).load(set.getString("mImage2")).into(pic3);
                    titleTxt.setText(set.getString("mName"));
                    genreTxt.setText(set.getString("mGenre"));
                    movieRateTxt.setText(String.valueOf(set.getInt("mRating")));
                    movieTimeTxt.setText(set.getString("mDuration"));
                    movieDateTxt.setText(set.getString("mDate"));
                    movieSummaryInfo.setText(set.getString("mDescription"));
                    movieActorsInfo.setText(set.getString("mActors"));

                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private void initView() {
        titleTxt = findViewById(R.id.movieNameTxt);
        progressBar=findViewById(R.id.detailLoading);
        scrollView = findViewById(R.id.scrollView3);
        //1:48:10 - Sua lai FrontEnd
        pic1 = findViewById(R.id.posterNormalImg);
        pic2 = findViewById(R.id.posterBigImg);
        pic3 = findViewById(R.id.Image2);
        movieRateTxt = findViewById(R.id.movieRateTxt);
        genreTxt = findViewById(R.id.genreTxt);
        movieTimeTxt = findViewById(R.id.movieTimeTxt);
        movieDateTxt = findViewById(R.id.movieDateTxt);
        movieSummaryInfo = findViewById(R.id.movieSummaryInfo);
        movieActorsInfo = findViewById(R.id.movieActorsInfo);
        backImg = findViewById(R.id.backImg);
        //recyclerView = findViewById(R.id.imagesRecyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        backImg.setOnClickListener(v -> finish());

    }

}