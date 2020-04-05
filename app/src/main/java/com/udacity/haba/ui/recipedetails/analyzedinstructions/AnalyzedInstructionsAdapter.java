package com.udacity.haba.ui.recipedetails.analyzedinstructions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Instructions;

import java.util.List;

public class AnalyzedInstructionsAdapter extends RecyclerView.Adapter<AnalyzedInstructionsViewHolder> {

    private List<Instructions> instructions;

    public AnalyzedInstructionsAdapter(List<Instructions> instructions) {
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public AnalyzedInstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.analyzed_instruction_list, parent, false);
        return new AnalyzedInstructionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnalyzedInstructionsViewHolder holder, int position) {
        holder.bind(instructions.get(position));
    }

    @Override
    public int getItemCount() {
        return instructions == null ? 0 : instructions.size();
    }
}
