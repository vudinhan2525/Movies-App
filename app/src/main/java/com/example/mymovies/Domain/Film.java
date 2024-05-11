package com.example.mymovies.Domain;

public class Film {
    public int id;
    public String title;
    public String score;
    public String imageUrl;
    public Film(int id,String title,String score,String imageUrl){
        this.id = id;
        this.title = title;
        this.score = score;
        this.imageUrl = imageUrl;
    }



}
