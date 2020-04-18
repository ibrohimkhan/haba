package com.udacity.haba.ui.ingredients;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.data.model.Ingredient;
import com.udacity.haba.databinding.IngredientItemListBinding;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    private IngredientItemListBinding binding;
    private Ingredient ingredient;

    public IngredientViewHolder(@NonNull View itemView, IngredientViewModel viewModel) {
        super(itemView);

        binding = IngredientItemListBinding.bind(itemView);

        binding.cbSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (ingredient == null) return;

            ingredient.setSelected(isChecked);
            viewModel.update(ingredient);
        });

        binding.ivDelete.setOnClickListener(v -> {
            if (ingredient == null) return;
            viewModel.delete(ingredient);
        });
    }

    public void bind(Ingredient ingredient) {
        if (ingredient == null) return;
        this.ingredient = ingredient;

        binding.tvIngredientName.setText(ingredient.getName());
        binding.cbSelected.setChecked(ingredient.isSelected());
    }
}
