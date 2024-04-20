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
import com.example.mymovies.Domain.FilmItem;
import com.example.mymovies.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, movieTimeTxt, movieDateTxt, movieSummaryInfo, movieActorsInfo;
    private NestedScrollView scrollView;
    private int idFilm;
    private ShapeableImageView pic1;
    private ImageView pic2, backImg;
    private RecyclerView.Adapter adapterImgList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        idFilm = getIntent().getIntExtra("id", 0);
        initView();
        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idFilm, response -> {
            Gson gson = new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            FilmItem items = gson.fromJson(response,FilmItem.class);
            Glide.with(DetailActivity.this).load(items.getPoster()).into(pic1);
            Glide.with(DetailActivity.this).load(items.getPoster()).into(pic2);
            titleTxt.setText(items.getTitle());
            movieRateTxt.setText(items.getRated());
            movieTimeTxt.setText(items.getRuntime());
            movieDateTxt.setText(items.getReleased());
            movieSummaryInfo.setText(items.getPlot());
            movieActorsInfo.setText(items.getActors());
            if(items.getImages() != null)
            {
                adapterImgList = new ImageListAdapter(items.getImages());
                recyclerView.setAdapter(adapterImgList);
            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            Log.i("uilover", "onErrorResponse" + error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void initView() {
        titleTxt = findViewById(R.id.movieNameTxt);
        progressBar=findViewById(R.id.detailLoading);
        scrollView = findViewById(R.id.scrollView3);
        //1:48:10 - Sua lai FrontEnd
        pic1 = findViewById(R.id.posterNormalImg);
        pic2 = findViewById(R.id.posterBigImg);
        movieRateTxt = findViewById(R.id.movieRateTxt);
        movieTimeTxt = findViewById(R.id.movieTimeTxt);
        movieDateTxt = findViewById(R.id.movieDateTxt);
        movieSummaryInfo = findViewById(R.id.movieSummaryInfo);
        movieActorsInfo = findViewById(R.id.movieActorsInfo);
        backImg = findViewById(R.id.backImg);
        recyclerView = findViewById(R.id.imagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        backImg.setOnClickListener(v -> finish());

    }

}