package com.udacity.haba.ui.eventlistener;

import com.udacity.haba.data.model.RecipeDetails;

import java.util.List;

public interface RecipeSelectionEventListener {
    void onRecipeSelectedEvent(int position, List<Long> items);
    void onFavoriteRecipeSelectedEvent(int position, List<RecipeDetails> items);
}
