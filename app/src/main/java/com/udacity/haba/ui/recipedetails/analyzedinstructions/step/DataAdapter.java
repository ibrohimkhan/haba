package com.udacity.haba.ui.recipedetails.analyzedinstructions.step;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Data;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {

    private List<Data> data;
    private String baseUrl;

    public DataAdapter(List<Data> data, String baseUrl) {
        this.data = data;
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item_list, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.bind(data.get(position), baseUrl);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
