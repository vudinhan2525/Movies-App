package com.example.mymovies.Domain;



public class Film  {
    public int userId;
    public boolean fromLikedPage;
    public int id;
    public String title;
    public String score;
    public String imageUrl;

    public boolean isLiked;

    public Film(int id,String title,String score,String imageUrl,int userId){
        this.id = id;
        this.title = title;
        this.score = score;
        this.imageUrl = imageUrl;
        this.isLiked = false;
        this.userId = userId;

    }
    public Film(int id,String title,String score,String imageUrl,int userId,boolean fromLikedPage){
        this.id = id;
        this.title = title;
        this.score = score;
        this.imageUrl = imageUrl;
        this.isLiked = false;
        this.userId = userId;
        this.fromLikedPage = fromLikedPage;
    }


}
