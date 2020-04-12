package com.udacity.haba.ui.recipedetails.analyzedinstructions.step;

import android.view.View;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Data;
import com.udacity.haba.ui.recipedetails.Adapter;

import java.util.List;

public class DataAdapter extends Adapter<Data, DataViewHolder> {

    private String baseUrl;

    public DataAdapter(List<Data> data, String baseUrl) {
        super(data);
        this.baseUrl = baseUrl;
    }

    @Override
    public int getResLayout() {
        return R.layout.data_item_list;
    }

    @Override
    public DataViewHolder getViewHolder(View view) {
        return new DataViewHolder(view);
    }

    @Override
    public void bind(DataViewHolder holder, int position) {
        holder.bind(items.get(position), baseUrl);
    }
}
