package com.udacity.haba.ui.recipedetails.extendedingredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Data;

import java.util.List;

public class ExtendedIngredientsAdapter extends RecyclerView.Adapter<ExtendedIngredientsViewHolder> {

    private List<Data> data;

    public ExtendedIngredientsAdapter(List<Data> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ExtendedIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.extended_ingredient_list, parent, false);
        return new ExtendedIngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtendedIngredientsViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
