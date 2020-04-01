package com.udacity.haba.data.model;

public class Recipe {

    public final long id;
    public final String title;
    public final String image;
    public final long likes;

    public Recipe(long id, String title, String image, long likes) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.likes = likes;
    }
}
