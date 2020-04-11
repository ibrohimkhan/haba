package com.udacity.haba.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VM, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> items;
    protected VM viewModel;

    public abstract VH getViewHolder(View view, VM viewModel);
    public abstract void bind(VH holder, int position);

    public BaseAdapter(List<T> items, VM viewModel) {
        this.items = items;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_list, parent, false);
        return getViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public List<T> getItems() {
        return items;
    }

    public void append(List<T> recipes) {
        if (items == null) {
            items = new ArrayList<>(recipes);
            notifyDataSetChanged();

        } else {
            int size = items.size();
            items.addAll(recipes);
            notifyItemRangeInserted(size, recipes.size());
        }
    }
}
