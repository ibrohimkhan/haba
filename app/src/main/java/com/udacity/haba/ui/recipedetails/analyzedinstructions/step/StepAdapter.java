package com.udacity.haba.ui.recipedetails.analyzedinstructions.step;

import android.view.View;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Step;
import com.udacity.haba.ui.recipedetails.Adapter;

import java.util.List;

public class StepAdapter extends Adapter<Step, StepViewHolder> {

    public StepAdapter(List<Step> steps) {
        super(steps);
    }

    @Override
    public int getResLayout() {
        return R.layout.step_item_list;
    }

    @Override
    public StepViewHolder getViewHolder(View view) {
        return new StepViewHolder(view);
    }

    @Override
    public void bind(StepViewHolder holder, int position) {
        holder.bind(items.get(position));
    }
}
