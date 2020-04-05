package com.udacity.haba.ui.recipedetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.udacity.haba.databinding.FragmentRecipeDetailsViewpagerBinding;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsViewPagerFragment extends Fragment {

    public static final String TAG = "RecipeDetailsViewPagerFragment";

    private static final String KEY_RECIPE_ID_POSITION = "selected_recipe_id";
    private static final String KEY_ALL_RECIPE_IDS = "all_recipe_ids";

    private int position;
    private List<Long> recipeIds;

    private RecipeDetailsViewPagerAdapter adapter;
    private FragmentRecipeDetailsViewpagerBinding binding;

    private ViewPager2.OnPageChangeCallback callback;
    private ViewPager2.PageTransformer animator;

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
            recipeIds = (List<Long>) bundle.getSerializable(KEY_ALL_RECIPE_IDS);
        }

        callback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        };

        animator = (page, position) -> {
            float absPos = Math.abs(position);
            float scale = absPos > 1 ? 0F : 1 - absPos;

            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setTranslationX(absPos * 350f);
            page.setTranslationY(absPos * 500f);
            page.setAlpha(0.5f + (scale - 0.4f) / (1 - 0.4f) * (1 - 0.5f));
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeDetailsViewpagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.vpRecipeDetails.registerOnPageChangeCallback(callback);
        binding.vpRecipeDetails.setPageTransformer(animator);

        adapter = new RecipeDetailsViewPagerAdapter(this, recipeIds);

        binding.vpRecipeDetails.setAdapter(adapter);
        binding.vpRecipeDetails.setCurrentItem(position, false);
    }

    @Override
    public void onDestroyView() {
        binding.vpRecipeDetails.unregisterOnPageChangeCallback(callback);
        super.onDestroyView();
    }
}
