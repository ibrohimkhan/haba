package com.udacity.haba.ui.recipedetails.analyzedinstructions;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.data.model.Instructions;
import com.udacity.haba.databinding.AnalyzedInstructionListBinding;
import com.udacity.haba.ui.recipedetails.analyzedinstructions.step.StepAdapter;

public class AnalyzedInstructionsViewHolder extends RecyclerView.ViewHolder {

    private AnalyzedInstructionListBinding binding;

    public AnalyzedInstructionsViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = AnalyzedInstructionListBinding.bind(itemView);
    }

    public void bind(Instructions instructions) {
        if (!TextUtils.isEmpty(instructions.name)) {
            binding.tvAnalyzedInstructionName.setVisibility(View.VISIBLE);
            binding.tvAnalyzedInstructionName.setText(instructions.name);
        }

        if (instructions.steps != null && !instructions.steps.isEmpty()) {
            binding.rvAnalyzedInstructionStep.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            StepAdapter adapter = new StepAdapter(instructions.steps);
            binding.rvAnalyzedInstructionStep.setAdapter(adapter);
        }
    }
}
