package com.udacity.haba.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StepResponse {

    @Expose
    @SerializedName("number")
    public final int number;

    @Expose
    @SerializedName("step")
    public final String step;

    @Expose
    @SerializedName("ingredients")
    public final List<StepDataResponse> ingredients;

    @Expose
    @SerializedName("equipment")
    public final List<StepDataResponse> equipment;

    public StepResponse(int number, String step, List<StepDataResponse> ingredients, List<StepDataResponse> equipment) {
        this.number = number;
        this.step = step;
        this.ingredients = ingredients;
        this.equipment = equipment;
    }
}
