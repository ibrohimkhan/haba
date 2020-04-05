package com.udacity.haba.ui.recipedetails;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.squareup.picasso.Picasso;
import com.udacity.haba.R;
import com.udacity.haba.databinding.FragmentRecipeDetailsBinding;
import com.udacity.haba.ui.recipedetails.analyzedinstructions.AnalyzedInstructionsAdapter;
import com.udacity.haba.ui.recipedetails.extendedingredients.ExtendedIngredientsAdapter;
import com.udacity.haba.utils.DialogUtils;

public class RecipeDetailsFragment extends Fragment {

    public static final String TAG = "RecipeDetailsFragment";
    private static final String KEY_SELECTED_RECIPE_ID = "selected_recipe_id";

    private long selectedId;
    private RecipeDetailsViewModel viewModel;

    private FragmentRecipeDetailsBinding binding;

    public static RecipeDetailsFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(KEY_SELECTED_RECIPE_ID, id);

        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RecipeDetailsViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_SELECTED_RECIPE_ID)) {
            selectedId = bundle.getLong(KEY_SELECTED_RECIPE_ID);
            viewModel.initialize(selectedId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.tbToolbar.setNavigationOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().onBackPressed();
        });

        binding.ctlCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        binding.rvExtendedIngredients.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvExtendedIngredients.setNestedScrollingEnabled(false);

        viewModel.loading.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;

            if (event.peek().booleanValue()) binding.pbLoader.setVisibility(View.VISIBLE);
            else binding.pbLoader.setVisibility(View.GONE);
        });

        viewModel.connectionError.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;
            if (event.peek()) {
                DialogUtils.showError(
                        requireActivity(),
                        getString(R.string.attention),
                        getString(R.string.network_connection_error)
                );
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;
            DialogUtils.showError(requireActivity(), getString(R.string.attention), event.peek());
        });

        viewModel.error.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;
            if (event.peek()) {
                DialogUtils.showError(
                        requireActivity(),
                        getString(R.string.attention),
                        getString(R.string.somthing_went_wrong)
                );
            }
        });

        viewModel.recipeDetails.observe(getViewLifecycleOwner(), recipeDetails -> {
            Picasso.get()
                    .load(recipeDetails.image)
                    .into(binding.ivRecipeDetailsImage);

            binding.ctlCollapsingToolbarLayout.setTitle(getString(R.string.back));

            binding.tvRecipeTitle.setText(recipeDetails.title);
            binding.tvReadyInMinutes.setText(recipeDetails.readyInMinutes + " minutes");
            binding.tvServings.setText(recipeDetails.servings + " servings");

            if (recipeDetails.extendedIngredients != null && !recipeDetails.extendedIngredients.isEmpty()) {
                binding.tvIngredientsTitle.setVisibility(View.VISIBLE);
                ExtendedIngredientsAdapter adapter = new ExtendedIngredientsAdapter(recipeDetails.extendedIngredients);
                binding.rvExtendedIngredients.setAdapter(adapter);
            }

            if (!TextUtils.isEmpty(recipeDetails.instructions)) {
                binding.tvInstructionTitle.setVisibility(View.VISIBLE);
                binding.tvInstruction.setText(HtmlCompat.fromHtml(recipeDetails.instructions, HtmlCompat.FROM_HTML_MODE_COMPACT));
            }

            if (recipeDetails.analyzedInstructions != null && !recipeDetails.analyzedInstructions.isEmpty()) {
                binding.tvAnalyzedInstructions.setVisibility(View.VISIBLE);

                binding.rvAnalyzedInstructions.setLayoutManager(new LinearLayoutManager(requireContext()));
                AnalyzedInstructionsAdapter adapter = new AnalyzedInstructionsAdapter(recipeDetails.analyzedInstructions);
                binding.rvAnalyzedInstructions.setAdapter(adapter);
            }
        });
    }
}
