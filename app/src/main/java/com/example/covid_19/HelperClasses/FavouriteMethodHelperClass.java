package com.example.covid_19.HelperClasses;

public class FavouriteMethodHelperClass {

    int image,heart;
    String title, description;

    public FavouriteMethodHelperClass(int image, int heart, String title, String description) {
        this.image = image;
        this.heart = heart;
        this.title = title;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public int getHeart() {
        return heart;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
