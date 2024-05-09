package com.example.mymovies.Domain;

public class ImageData {
    private String imagePath;

    public ImageData(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
