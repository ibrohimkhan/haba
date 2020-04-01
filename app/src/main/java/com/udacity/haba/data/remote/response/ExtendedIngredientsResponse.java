package com.udacity.haba.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtendedIngredientsResponse {

    @Expose
    @SerializedName("id")
    public final long id;

    @Expose
    @SerializedName("image")
    public final String image;

    @Expose
    @SerializedName("originalString")
    public final String name;

    public ExtendedIngredientsResponse(long id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }
}
