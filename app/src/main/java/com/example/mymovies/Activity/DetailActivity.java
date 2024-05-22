package com.example.mymovies.Activity;

import static java.lang.String.valueOf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.example.mymovies.MainActivity;
import com.example.mymovies.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
    private CheckBox likeCheckBox;
    private int idFilm;
    private ShapeableImageView pic1;
    private ImageView pic2, backImg, pic3;
    private RecyclerView.Adapter adapterImgList, imagesRecyclerView;
    private RecyclerView recyclerView;
    private int userId;
    private ListFilmAdapter listFilmAdapter;
    WebView wv;
    VideoView vw;
   // private List<Film> filmList1;
   // private ListFilmAdapter listFilmAdapter;
    Connection connection;
    private List<Film> RelatedFilmList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        idFilm = getIntent().getIntExtra("id", 0);
        userId = getIntent().getIntExtra("userId", 0);

        initView();
        Log.i("id", valueOf(idFilm));

        sendRequest();

        // Initialize views
        likeCheckBox = findViewById(R.id.likeCheckBox);
        // Load liked status from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("liked_films", MODE_PRIVATE);
        boolean isLiked = sharedPreferences.getBoolean("film_" + idFilm, false);
        likeCheckBox.setChecked(isLiked);

        // Handle like checkbox click
        likeCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save liked status to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("film_" + idFilm, isChecked);
            editor.apply();
            String res = String.valueOf(isChecked);
            ConnectionDB db = new ConnectionDB();
            connection = db.conclass();
            if(isChecked == true)
            {
                if (db != null) {
                    try {
                        String sqlQuery = "INSERT INTO UserLike(userId,filmId) VALUES(" + userId + "," + idFilm +")";
                        Statement smt = connection.createStatement();
                        ResultSet set = smt.executeQuery(sqlQuery);
                        connection.close();

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    Toast.makeText(DetailActivity.this,"liked!",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if (db != null) {
                    try {
                        String sqlQuery = "Delete From UserLike Where userId = " + userId + " and filmId = " + idFilm + ";";
                        Statement smt = connection.createStatement();
                        ResultSet set = smt.executeQuery(sqlQuery);
                        connection.close();

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    Toast.makeText(DetailActivity.this,"cancel liked!",Toast.LENGTH_SHORT).show();
                }
            }

        });
        boolean fromLikedPage = getIntent().getBooleanExtra("fromLikedPage", false);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromLikedPage == true){
                    Intent intent = new Intent(DetailActivity.this, FavoriteActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    intent.putExtra("USERID", userId);
                    startActivity(intent);
                }
            }
        });
        fetchRelatedResults();
    }

    private void fetchRelatedResults() {
        ConnectionDB db = new ConnectionDB();
        connection = db.conclass();
        String[] genre = genreTxt.getText().toString().split(",");
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM Movies WHERE mGenre ");
        for (int i = 0; i < genre.length; i++) {
           if(i == 0){
               String query = "LIKE '%" + genre[i] + "%'";
               sqlQuery.append(query);
           }
           else{
               String query = " OR mGenre LIKE '%" + genre[i] + "%'";
               sqlQuery.append(query);
           }

        }

        if (db != null) {
            try {

                Statement smt = connection.createStatement();
                ResultSet set = smt.executeQuery(sqlQuery.toString());
                RelatedFilmList = new ArrayList<>();
                while (set.next()) {
                    int mId = set.getInt("mId");
                    String mImage = set.getString("mImage");
                    String mName = set.getString("mName");
                    int mRating = set.getInt("mRating");
                    String mScore = Integer.toString(mRating);
                    Film film = new Film(mId, mName, mScore, mImage,userId);
                    RelatedFilmList.add(film);
                }
                connection.close();
                listFilmAdapter = new ListFilmAdapter(RelatedFilmList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerViewRelatedResults = findViewById(R.id.relatedFilm);
                recyclerViewRelatedResults.setLayoutManager(linearLayoutManager);
                recyclerViewRelatedResults.setAdapter(listFilmAdapter);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
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
                    Picasso.get().load(set.getString("mImage")).into(pic1);
                    Picasso.get().load(set.getString("mImage1")).into(pic2);
                    Picasso.get().load(set.getString("mImage2")).into(pic3);

                    titleTxt.setText(set.getString("mName"));
                    genreTxt.setText(set.getString("mGenre"));
                    movieRateTxt.setText(valueOf(set.getInt("mRating")));
                    movieTimeTxt.setText(set.getString("mDuration"));
                    movieDateTxt.setText(set.getString("mDate"));
                    movieSummaryInfo.setText(set.getString("mDescription"));
                    movieActorsInfo.setText(set.getString("mActors"));

                    String video = set.getString("mUrlVideo");
                    wv.loadData(video,"text/html","utf-8");
                    WebSettings webSettings= wv.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    wv.setWebChromeClient(new WebChromeClient());
//                    MediaController mc = new MediaController(this);
//                    Uri uri = Uri.parse(video);
//                    mc.setAnchorView(vw);
//                    vw.setMediaController(mc);
//                    vw.setVideoURI(uri);
//                    vw.requestFocus();
//                    vw.start();

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
        wv = findViewById(R.id.mainVideo);
        //vw = findViewById(R.id.mainVideo);
        //backImg.setOnClickListener(v -> finish());

    }

}