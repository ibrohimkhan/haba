package com.udacity.haba.ui.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.udacity.haba.data.model.RecipeDetails;
import com.udacity.haba.ui.recipedetails.RecipeDetailsFragment;

import java.util.List;

public class BaseFragmentStateAdapter<T> extends FragmentStateAdapter {

    private List<T> items;

    public BaseFragmentStateAdapter(@NonNull Fragment fragment, List<T> items) {
        super(fragment);
        this.items = items;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        T item = items.get(position);

        if (item instanceof Long)
            return RecipeDetailsFragment.newInstance((Long) item);

        else if (item instanceof RecipeDetails)
            return RecipeDetailsFragment.newInstance((RecipeDetails) item);

        else
            throw new RuntimeException("There is no Fragment was found");
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
