package com.udacity.haba.ui.recipedetails.extendedingredients;

import android.view.View;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Data;
import com.udacity.haba.ui.recipedetails.Adapter;

import java.util.List;

public class ExtendedIngredientsAdapter extends Adapter<Data, ExtendedIngredientsViewHolder> {

    public ExtendedIngredientsAdapter(List<Data> data) {
        super(data);
    }

    @Override
    public int getResLayout() {
        return R.layout.extended_ingredient_list;
    }

    @Override
    public ExtendedIngredientsViewHolder getViewHolder(View view) {
        return new ExtendedIngredientsViewHolder(view);
    }

    @Override
    public void bind(ExtendedIngredientsViewHolder holder, int position) {
        holder.bind(items.get(position));
    }
}
