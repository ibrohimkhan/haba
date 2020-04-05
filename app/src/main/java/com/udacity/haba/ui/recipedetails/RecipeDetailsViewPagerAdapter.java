package com.udacity.haba.ui.recipedetails;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class RecipeDetailsViewPagerAdapter extends FragmentStateAdapter {

    private List<Long> recipeIds;

    public RecipeDetailsViewPagerAdapter(@NonNull Fragment fragment, List<Long> recipeIds) {
        super(fragment);
        this.recipeIds = recipeIds;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        long id = recipeIds.get(position);
        return RecipeDetailsFragment.newInstance(id);
    }

    @Override
    public int getItemCount() {
        return recipeIds == null ? 0 : recipeIds.size();
    }
}
