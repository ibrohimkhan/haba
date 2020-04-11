package com.udacity.haba.ui.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.haba.data.model.Recipe;
import com.udacity.haba.databinding.RecipeItemListBinding;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecipeItemListBinding binding;
    protected int position;
    protected T viewModel;

    public BaseViewHolder(@NonNull View itemView, T viewModel) {
        super(itemView);
        this.viewModel = viewModel;

        itemView.setOnClickListener(this);
        binding = RecipeItemListBinding.bind(itemView);
    }

    public void bind(Recipe recipe, int position) {
        this.position = position;

        Picasso.get()
                .load(recipe.image)
                .into(binding.ivRecipeImage);

        binding.tvRecipeTitle.setText(recipe.title);
        binding.tvRecipeLikes.setText(String.valueOf(recipe.likes));
    }
}
