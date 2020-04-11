package com.udacity.haba.ui.favorites;

import android.view.View;

import androidx.annotation.NonNull;

import com.udacity.haba.ui.base.BaseViewHolder;
import com.udacity.haba.utils.Event;

public class FavoriteViewHolder extends BaseViewHolder<FavoriteRecipeViewModel> {

    public FavoriteViewHolder(@NonNull View itemView, FavoriteRecipeViewModel viewModel) {
        super(itemView, viewModel);
    }

    @Override
    public void onClick(View v) {
        viewModel.onRecipeSelected.setValue(new Event<>(position));
    }
}
