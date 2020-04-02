package com.udacity.haba.ui.recipes;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.haba.data.model.Recipe;
import com.udacity.haba.databinding.RecipeItemListBinding;
import com.udacity.haba.utils.Event;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecipeItemListBinding binding;

    private RecipeViewModel viewModel;
    private Recipe recipe;

    public RecipeViewHolder(@NonNull View itemView, RecipeViewModel viewModel) {
        super(itemView);
        this.viewModel = viewModel;

        binding = RecipeItemListBinding.bind(itemView);
    }

    public void bind(Recipe recipe) {
        this.recipe = recipe;

        Picasso.get()
                .load(recipe.image)
                .into(binding.ivRecipeImage);

        binding.tvRecipeTitle.setText(recipe.title);
        binding.tvRecipeLikes.setText(String.valueOf(recipe.likes));
    }

    @Override
    public void onClick(View v) {
        viewModel.onRecipeSelected.setValue(new Event<>(recipe.id));
    }
}
