package com.udacity.haba.ui.recipes;

import android.view.View;

import com.udacity.haba.data.model.Recipe;
import com.udacity.haba.ui.base.BaseAdapter;

import java.util.List;

public class RecipeAdapter extends BaseAdapter<Recipe, RecipeViewModel, RecipeViewHolder> {

    public RecipeAdapter(List<Recipe> recipes, RecipeViewModel viewModel) {
        super(recipes, viewModel);
    }

    @Override
    public RecipeViewHolder getViewHolder(View view, RecipeViewModel viewModel) {
        return new RecipeViewHolder(view, viewModel);
    }

    @Override
    public void bind(RecipeViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }
}
