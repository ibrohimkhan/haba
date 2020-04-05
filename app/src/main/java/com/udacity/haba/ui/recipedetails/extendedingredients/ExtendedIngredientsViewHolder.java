package com.udacity.haba.ui.recipedetails.extendedingredients;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.haba.BuildConfig;
import com.udacity.haba.data.model.Data;
import com.udacity.haba.databinding.ExtendedIngredientListBinding;

public class ExtendedIngredientsViewHolder extends RecyclerView.ViewHolder {

    private ExtendedIngredientListBinding binding;

    public ExtendedIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ExtendedIngredientListBinding.bind(itemView);
    }

    public void bind(Data data) {
        Picasso.get()
                .load(BuildConfig.INGREDIENTS_IMAGE_URL + data.image)
                .into(binding.ivExtendedIngredient);

        binding.tvExtendedIngredient.setText(data.name);
    }
}
