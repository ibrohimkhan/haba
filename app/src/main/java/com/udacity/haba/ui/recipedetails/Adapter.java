package com.udacity.haba.ui.recipedetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class Adapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> items;

    @LayoutRes
    public abstract int getResLayout();

    public abstract VH getViewHolder(View view);

    public abstract void bind(VH holder, int position);

    public Adapter(List<T> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getResLayout(), parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
