package com.udacity.haba.ui.ingredients;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udacity.haba.data.model.Ingredient;
import com.udacity.haba.databinding.FragmentIngredientsBinding;

import java.util.ArrayList;

public class IngredientFragment extends Fragment {

    public static final String TAG = "IngredientFragment";

    private FragmentIngredientsBinding binding;
    private IngredientViewModel viewModel;
    private IngredientAdapter adapter;

    public static IngredientFragment newInstance() {
        Bundle args = new Bundle();
        IngredientFragment fragment = new IngredientFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new IngredientAdapter(new ArrayList<>(), viewModel);
        binding.rvIngredients.setAdapter(adapter);

        binding.etIngredients.setOnTouchListener((v, event) -> {
            if (!TextUtils.isEmpty(binding.etIngredients.getText().toString())) {
                binding.llActionControls.setVisibility(View.VISIBLE);
            }

            return false;
        });

        binding.etIngredients.setKeyImeChangeListener((keyCode, event) -> {
            if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
                binding.llActionControls.setVisibility(View.GONE);
            }
        });

        binding.etIngredients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.filter(s);

                if (!TextUtils.isEmpty(s)) {
                    binding.llActionControls.setVisibility(View.VISIBLE);

                } else {
                    binding.llActionControls.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.tvClearAction.setOnClickListener(v -> {
            binding.etIngredients.setText("");
        });

        binding.tvAddAction.setOnClickListener(v -> {
            Editable editable = binding.etIngredients.getText();
            String ingredient = editable == null ? null : editable.toString().trim();

            if (TextUtils.isEmpty(ingredient)) return;

            Ingredient item = new Ingredient(ingredient, false);
            viewModel.save(item);

            binding.etIngredients.setText("");
            binding.llActionControls.setVisibility(View.GONE);
        });

        viewModel.ingredients.observe(getViewLifecycleOwner(), ingredients -> {
            adapter.update(ingredients);
        });

        viewModel.filtered.observe(getViewLifecycleOwner(), ingredients -> {
            adapter.filter(ingredients);
        });

        viewModel.completed.observe(getViewLifecycleOwner(), completed -> {
            viewModel.reload();
        });
    }
}
