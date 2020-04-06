package com.udacity.haba.ui.recipedetails.analyzedinstructions.step;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.haba.data.model.Data;
import com.udacity.haba.databinding.DataItemListBinding;

public class DataViewHolder extends RecyclerView.ViewHolder {

    private DataItemListBinding binding;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataItemListBinding.bind(itemView);
    }

    public void bind(Data data, String baseUrl) {
        if (!TextUtils.isEmpty(data.image)) {
            Picasso.get()
                    .load(baseUrl + data.image)
                    .into(binding.ivItemImage);
        }

        if (!TextUtils.isEmpty(data.name))
            binding.tvItemName.setText(data.name);
    }
}
