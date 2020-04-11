package com.udacity.haba.ui.recipedetails;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.udacity.haba.ui.base.BaseFragmentStateAdapter;
import com.udacity.haba.ui.base.BaseViewPagerFragment;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsViewPagerFragment extends BaseViewPagerFragment<Long> {

    public static final String TAG = "RecipeDetailsViewPagerFragment";

    private static final String KEY_RECIPE_ID_POSITION = "selected_recipe_id";
    private static final String KEY_ALL_RECIPE_IDS = "all_recipe_ids";

    public static RecipeDetailsViewPagerFragment newInstance(int position, List<Long> recipeIds) {
        Bundle args = new Bundle();
        args.putInt(KEY_RECIPE_ID_POSITION, position);
        args.putSerializable(KEY_ALL_RECIPE_IDS, (Serializable) recipeIds);

        RecipeDetailsViewPagerFragment fragment = new RecipeDetailsViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_RECIPE_ID_POSITION) && bundle.containsKey(KEY_ALL_RECIPE_IDS)) {
            position = bundle.getInt(KEY_RECIPE_ID_POSITION);
            items = (List<Long>) bundle.getSerializable(KEY_ALL_RECIPE_IDS);
        }
    }

    @Override
    public BaseFragmentStateAdapter<Long> setupAdapter() {
        return new BaseFragmentStateAdapter<>(this, items);
    }
}
