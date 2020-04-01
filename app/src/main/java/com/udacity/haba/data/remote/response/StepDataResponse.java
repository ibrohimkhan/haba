package com.udacity.haba.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StepDataResponse {

    @Expose
    @SerializedName("id")
    public final long id;

    @Expose
    @SerializedName("name")
    public final String name;

    @Expose
    @SerializedName("image")
    public final String image;

    public StepDataResponse(long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
