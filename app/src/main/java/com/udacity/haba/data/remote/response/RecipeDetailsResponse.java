package com.udacity.haba.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeDetailsResponse {

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
    @SerializedName("imageType")
    public final String imageType;

    @Expose
    @SerializedName("readyInMinutes")
    public final int readyInMinutes;

    @Expose
    @SerializedName("servings")
    public final int servings;

    @Expose
    @SerializedName("extendedIngredients")
    public final List<ExtendedIngredientsResponse> extendedIngredients;

    @Expose
    @SerializedName("instructions")
    public final String instructions;

    @Expose
    @SerializedName("analyzedInstructions")
    public final List<InstructionsResponse> analyzedInstructions;

    public RecipeDetailsResponse(long id, String title, String image, String imageType, int readyInMinutes, int servings, List<ExtendedIngredientsResponse> extendedIngredients, String instructions, List<InstructionsResponse> analyzedInstructions) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.extendedIngredients = extendedIngredients;
        this.instructions = instructions;
        this.analyzedInstructions = analyzedInstructions;
    }
}
