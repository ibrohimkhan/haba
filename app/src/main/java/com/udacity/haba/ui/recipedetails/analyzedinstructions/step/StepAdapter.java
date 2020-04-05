package com.udacity.haba.ui.recipedetails.analyzedinstructions.step;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private List<Step> steps;

    public StepAdapter(List<Step> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item_list, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }
}
