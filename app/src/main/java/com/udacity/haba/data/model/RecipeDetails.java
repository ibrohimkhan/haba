package com.udacity.haba.data.model;

import java.util.List;

public class RecipeDetails {

    public final long id;
    public final String title;
    public final String image;
    public final String imageType;
    public final int readyInMinutes;
    public final int servings;
    public final List<Data> extendedIngredients;
    public final String instructions;
    public final List<Instructions> analyzedInstructions;

    public RecipeDetails(long id, String title, String image, String imageType, int readyInMinutes, int servings, List<Data> extendedIngredients, String instructions, List<Instructions> analyzedInstructions) {
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
