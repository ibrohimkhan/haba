package com.udacity.haba.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstructionsResponse {

    @Expose
    @SerializedName("name")
    public final String name;

    @Expose
    @SerializedName("steps")
    public final List<StepResponse> steps;

    public InstructionsResponse(String name, List<StepResponse> steps) {
        this.name = name;
        this.steps = steps;
    }
}
