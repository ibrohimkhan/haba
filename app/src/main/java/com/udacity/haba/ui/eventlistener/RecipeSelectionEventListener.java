package com.udacity.haba.ui.eventlistener;

import java.util.List;

public interface RecipeSelectionEventListener {
    void onRecipeSelectedEvent(int position, List<Long> recipeIds);
}
