package com.udacity.haba.ui.ingredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private List<Ingredient> ingredients;
    private IngredientViewModel viewModel;

    public IngredientAdapter(List<Ingredient> ingredients, IngredientViewModel viewModel) {
        this.ingredients = ingredients;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_list, parent, false);
        return new IngredientViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    public void update(List<Ingredient> items) {
        if (items == null) return;

        int size = ingredients.size();
        ingredients.clear();
        notifyItemRangeRemoved(0, size);

        ingredients.addAll(items);
        notifyItemRangeInserted(0, items.size());
    }

    public void filter(List<Ingredient> items) {
        if (items == null) return;

        int size = ingredients.size();
        ingredients.clear();
        notifyItemRangeRemoved(0, size);

        ingredients.addAll(items);
        notifyItemRangeInserted(0, items.size());
    }
}
