package com.udacity.haba.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {

    @Expose
    @SerializedName("id")
    public final long id;

    @Expose
    @SerializedName("title")
    public final String title;

    @Expose
    @SerializedName("image")
    public final String image;

    @Expose
    @SerializedName("likes")
    public final long likes;

    public RecipeResponse(long id, String title, String image, long likes) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.likes = likes;
    }
}
