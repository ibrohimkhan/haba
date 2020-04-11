package com.udacity.haba.ui.recipes;

import android.view.View;

import com.udacity.haba.data.model.RecipeDetails;
import com.udacity.haba.ui.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class RandomRecipeAdapter extends BaseAdapter<RecipeDetails, RecipeViewModel, RecipeViewHolder> {

    public RandomRecipeAdapter(List<RecipeDetails> items, RecipeViewModel viewModel) {
        super(items, viewModel);
    }

    @Override
    public RecipeViewHolder getViewHolder(View view, RecipeViewModel viewModel) {
        return new RecipeViewHolder(view, viewModel);
    }

    @Override
    public void bind(RecipeViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    public void updateRecipes(List<RecipeDetails> recipes) {
        if (items == null) {
            items = new ArrayList<>(recipes);
            notifyDataSetChanged();

        } else {
            int size = items.size();

            items.clear();
            notifyItemRangeRemoved(0, size);

            items.addAll(recipes);
            notifyItemRangeInserted(0, recipes.size());
        }
    }
}
