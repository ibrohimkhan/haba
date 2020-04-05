package com.udacity.haba.ui.recipedetails.analyzedinstructions.step;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.data.model.Step;
import com.udacity.haba.databinding.StepItemListBinding;

public class StepViewHolder extends RecyclerView.ViewHolder {

    private StepItemListBinding binding;

    public StepViewHolder(@NonNull View itemView) {
        super(itemView);

        binding = StepItemListBinding.bind(itemView);
        binding.rvStepEquipment.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        binding.rvStepIngredient.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
    }

    public void bind(Step step) {
        binding.tvStepNumber.setText("Step " + step.number + ":");
        binding.tvStepTitle.setText(step.step);

        if (step.equipment != null && !step.equipment.isEmpty()) {
            binding.tvEquipmentStep.setVisibility(View.VISIBLE);
            DataAdapter adapter = new DataAdapter(step.equipment, false);
            binding.rvStepEquipment.setAdapter(adapter);
        }

        if (step.ingredients != null && !step.ingredients.isEmpty()) {
            binding.tvIngredientStep.setVisibility(View.VISIBLE);
            DataAdapter adapter = new DataAdapter(step.ingredients, true);
            binding.rvStepIngredient.setAdapter(adapter);
        }
    }
}
