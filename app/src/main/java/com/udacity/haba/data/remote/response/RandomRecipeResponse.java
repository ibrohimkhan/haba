package com.udacity.haba.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomRecipeResponse {

    @Expose
    @SerializedName("recipes")
    public final List<RecipeDetailsResponse> recipes;

    public RandomRecipeResponse(List<RecipeDetailsResponse> recipes) {
        this.recipes = recipes;
    }
}
