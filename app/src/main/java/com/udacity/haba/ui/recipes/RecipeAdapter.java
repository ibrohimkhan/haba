package com.udacity.haba.ui.recipes;

import android.view.View;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Recipe;
import com.udacity.haba.ui.base.BaseAdapter;

import java.util.ArrayList;
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

    @Override
    public int getResLayout() {
        return R.layout.recipe_item_list;
    }

    public void update(List<Recipe> recipes) {
        if (recipes == null || recipes.isEmpty()) return;

        if (items == null) items = new ArrayList<>();

        items.clear();
        items.addAll(recipes);

        notifyDataSetChanged();
    }
}
