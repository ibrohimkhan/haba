package com.udacity.haba.ui.favorites;

import android.view.View;

import com.udacity.haba.R;
import com.udacity.haba.data.model.RecipeDetails;
import com.udacity.haba.ui.base.BaseAdapter;

import java.util.List;

public class FavoriteAdapter extends BaseAdapter<RecipeDetails, FavoriteRecipeViewModel, FavoriteViewHolder> {

    public FavoriteAdapter(List<RecipeDetails> recipes, FavoriteRecipeViewModel viewModel) {
        super(recipes, viewModel);
    }

    @Override
    public FavoriteViewHolder getViewHolder(View view, FavoriteRecipeViewModel viewModel) {
        return new FavoriteViewHolder(view, viewModel);
    }

    @Override
    public void bind(FavoriteViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getResLayout() {
        return R.layout.recipe_item_list;
    }

    public void remove(RecipeDetails recipeDetails) {
        items.remove(recipeDetails);
        notifyDataSetChanged();
    }
}
