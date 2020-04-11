package com.udacity.haba.ui.favorites;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.udacity.haba.data.model.RecipeDetails;
import com.udacity.haba.ui.base.BaseFragmentStateAdapter;
import com.udacity.haba.ui.base.BaseViewPagerFragment;

import java.io.Serializable;
import java.util.List;

public class FavoriteDetailsViewPagerFragment extends BaseViewPagerFragment<RecipeDetails> {

    public static final String TAG = "FavoriteDetailsViewPagerFragment";

    private static final String KEY_RECIPE_ID_POSITION = "selected_recipe_id";
    private static final String KEY_ALL_RECIPE_DETAILS = "all_recipe_details";

    public static FavoriteDetailsViewPagerFragment newInstance(int position, List<RecipeDetails> items) {
        Bundle args = new Bundle();
        args.putInt(KEY_RECIPE_ID_POSITION, position);
        args.putSerializable(KEY_ALL_RECIPE_DETAILS, (Serializable) items);

        FavoriteDetailsViewPagerFragment fragment = new FavoriteDetailsViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_RECIPE_ID_POSITION) && bundle.containsKey(KEY_ALL_RECIPE_DETAILS)) {
            position = bundle.getInt(KEY_RECIPE_ID_POSITION);
            items = (List<RecipeDetails>) bundle.getSerializable(KEY_ALL_RECIPE_DETAILS);
        }
    }

    @Override
    public BaseFragmentStateAdapter<RecipeDetails> setupAdapter() {
        return new BaseFragmentStateAdapter<>(this, items);
    }
}
