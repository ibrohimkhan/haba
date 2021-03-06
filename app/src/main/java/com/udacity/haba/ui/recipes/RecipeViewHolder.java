package com.udacity.haba.ui.recipes;

import android.view.View;

import androidx.annotation.NonNull;

import com.udacity.haba.ui.base.BaseViewHolder;
import com.udacity.haba.utils.Event;

public class RecipeViewHolder extends BaseViewHolder<RecipeViewModel> {

    public RecipeViewHolder(@NonNull View itemView, RecipeViewModel viewModel) {
        super(itemView, viewModel);
    }

    @Override
    public void onClick(View v) {
        viewModel.onRecipeSelected.setValue(new Event<>(position));
    }
}
