package com.udacity.haba.ui.recipes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Recipe;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private List<? extends Recipe> recipes;
    private RecipeViewModel viewModel;

    public RecipeAdapter(List<? extends Recipe> recipes, RecipeViewModel viewModel) {
        this.recipes = recipes;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_list, parent, false);
        return new RecipeViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public void updateRecipes(List<? extends Recipe> items) {
        LinkedHashSet<? extends Recipe> newRecipes = new LinkedHashSet<>(items);

        int size = recipes.size();
        recipes.clear();
        notifyItemRangeRemoved(0, size);

        recipes = new ArrayList<>(newRecipes);
        notifyItemRangeInserted(0, recipes.size());
    }

    public void append(List<? extends Recipe> items) {
        LinkedHashSet<Recipe> newRecipes = new LinkedHashSet<>();
        newRecipes.addAll(recipes);
        newRecipes.addAll(items);

        int size = recipes.size();
        recipes.clear();
        notifyItemRangeRemoved(0, size);

        recipes = new ArrayList<>(newRecipes);
        notifyItemRangeInserted(0, recipes.size());
    }
}
