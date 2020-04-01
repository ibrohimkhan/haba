package com.udacity.haba.data.model;

import java.util.List;

public class RandomRecipe {

    public final List<RecipeDetails> recipes;

    public RandomRecipe(List<RecipeDetails> recipes) {
        this.recipes = recipes;
    }
}
