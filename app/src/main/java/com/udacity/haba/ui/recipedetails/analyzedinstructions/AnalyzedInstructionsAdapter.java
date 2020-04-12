package com.udacity.haba.ui.recipedetails.analyzedinstructions;

import android.view.View;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Instructions;
import com.udacity.haba.ui.recipedetails.Adapter;

import java.util.List;

public class AnalyzedInstructionsAdapter extends Adapter<Instructions, AnalyzedInstructionsViewHolder> {

    public AnalyzedInstructionsAdapter(List<Instructions> instructions) {
        super(instructions);
    }

    @Override
    public int getResLayout() {
        return R.layout.analyzed_instruction_list;
    }

    @Override
    public AnalyzedInstructionsViewHolder getViewHolder(View view) {
        return new AnalyzedInstructionsViewHolder(view);
    }

    @Override
    public void bind(AnalyzedInstructionsViewHolder holder, int position) {
        holder.bind(items.get(position));
    }
}
