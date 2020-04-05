package com.udacity.haba.ui.recipedetails.analyzedinstructions.step;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.haba.BuildConfig;
import com.udacity.haba.data.model.Data;
import com.udacity.haba.databinding.DataItemListBinding;

public class DataViewHolder extends RecyclerView.ViewHolder {

    private DataItemListBinding binding;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataItemListBinding.bind(itemView);
    }

    public void bind(Data data, boolean isIngredient) {
        if (!TextUtils.isEmpty(data.image)) {

            String url = null;
            if (isIngredient)
                url = BuildConfig.INGREDIENTS_IMAGE_URL + data.image;
            else
                url = BuildConfig.EQUIPMENT_IMAGE_URL + data.image;

            Picasso.get()
                    .load(url)
                    .into(binding.ivItemImage);
        }

        if (!TextUtils.isEmpty(data.name))
            binding.tvItemName.setText(data.name);
    }
}
