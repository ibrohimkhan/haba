package com.udacity.haba.ui.eventlistener;

import com.udacity.haba.data.model.RecipeDetails;

public interface RecipeRemoveEventNotifier {
    void notifyRecipeRemoveEvent(RecipeDetails recipeDetails);
}
