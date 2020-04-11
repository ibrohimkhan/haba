package com.udacity.haba.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.udacity.haba.databinding.FragmentRecipeDetailsViewpagerBinding;

import java.util.List;

public abstract class BaseViewPagerFragment<T> extends Fragment {

    protected int position;
    protected List<T> items;

    protected BaseFragmentStateAdapter<T> adapter;
    private FragmentRecipeDetailsViewpagerBinding binding;

    private ViewPager2.OnPageChangeCallback callback;
    private ViewPager2.PageTransformer animator;

    public abstract BaseFragmentStateAdapter<T> setupAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        adapter = setupAdapter();
        binding.vpRecipeDetails.setAdapter(adapter);
        binding.vpRecipeDetails.setCurrentItem(position, false);
    }

    @Override
    public void onDestroyView() {
        binding.vpRecipeDetails.unregisterOnPageChangeCallback(callback);
        super.onDestroyView();
    }
}
