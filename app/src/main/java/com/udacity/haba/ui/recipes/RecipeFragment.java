package com.udacity.haba.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.R;
import com.udacity.haba.data.model.Ingredient;
import com.udacity.haba.databinding.FragmentRecipeBinding;
import com.udacity.haba.ui.eventlistener.RecipeSelectionEventListener;
import com.udacity.haba.utils.DialogUtils;

import java.util.List;

public class RecipeFragment extends Fragment {

    public static final String TAG = "RecipeFragment";
    private static final String REFRESH_KEY = "refreshing_key";
    private static final String RECIPE_MODE_KEY = "recipe_mode_key";

    private FragmentRecipeBinding binding;
    private RecipeViewModel viewModel;
    private RandomRecipeAdapter randomRecipeAdapter;
    private RecipeAdapter recipeAdapter;
    private LinearLayoutManager linearLayoutManager;

    private boolean withIngredients = true;
    private List<Ingredient> ingredients;

    private boolean scrolling;
    private int rvPosition;

    private RecipeSelectionEventListener listener;

    public static RecipeFragment newInstance() {
        Bundle args = new Bundle();

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
            listener = (RecipeSelectionEventListener) getActivity();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(REFRESH_KEY, binding.srSwipeToRefresh.isRefreshing());
        outState.putBoolean(RECIPE_MODE_KEY, withIngredients);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeBinding.inflate(inflater, container, false);

        binding.tbRecipe.inflateMenu(R.menu.settings);
        binding.tbRecipe.setOnMenuItemClickListener(this::onOptionsItemSelected);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.rvRecipes.setLayoutManager(linearLayoutManager);

        randomRecipeAdapter = new RandomRecipeAdapter(null, viewModel);
        recipeAdapter = new RecipeAdapter(null, viewModel);

        binding.rvRecipes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!withIngredients && linearLayoutManager.getItemCount() == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    scrolling = true;
                    rvPosition = linearLayoutManager.findLastVisibleItemPosition();

                    viewModel.loadRandomRecipe();
                }
            }
        });

        binding.srSwipeToRefresh.setProgressViewEndTarget(false, 0);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(REFRESH_KEY)) {
                boolean isRefreshing = savedInstanceState.getBoolean(REFRESH_KEY);
                binding.srSwipeToRefresh.setRefreshing(isRefreshing);
            }

            if (savedInstanceState.containsKey(RECIPE_MODE_KEY)) {
                withIngredients = savedInstanceState.getBoolean(RECIPE_MODE_KEY);
            }
        }

        if (withIngredients) {
            binding.tvTitle.setText(getString(R.string.custom));
            binding.rvRecipes.setAdapter(recipeAdapter);

        } else {
            binding.tvTitle.setText(getString(R.string.random));
            binding.rvRecipes.setAdapter(randomRecipeAdapter);
        }

        binding.srSwipeToRefresh.setOnRefreshListener(() -> {
            if (!withIngredients) viewModel.loadRandomRecipe();
            else viewModel.loadCustomRecipe(ingredients);
        });

        viewModel.loading.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;

            if (event.peek()) binding.pbLoader.setVisibility(View.VISIBLE);
            else binding.pbLoader.setVisibility(View.GONE);

            binding.srSwipeToRefresh.setRefreshing(event.peek());
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

        viewModel.randomRecipes.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;

            if (randomRecipeAdapter.getItems() == null) {
                randomRecipeAdapter.append(event.peek().recipes);

            } else if (scrolling) {
                scrolling = false;
                randomRecipeAdapter.append(event.peek().recipes);
                binding.rvRecipes.scrollToPosition(rvPosition);

            } else  {
                randomRecipeAdapter.updateRecipes(event.peek().recipes);
            }
        });

        viewModel.recipes.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;

            if (recipeAdapter.getItems() == null) {
                recipeAdapter.append(event.peek());

            } else if (scrolling) {
                scrolling = false;
                recipeAdapter.append(event.peek());
                binding.rvRecipes.scrollToPosition(rvPosition);

            } else {
                recipeAdapter.update(event.peek());
            }
        });

        viewModel.onRecipeSelected.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;
            listener.onRecipeSelectedEvent(
                    event.peek().intValue(),
                    viewModel.getRecipeIds(
                            withIngredients ? recipeAdapter.getItems() : randomRecipeAdapter.getItems()
                    )
            );
        });

        viewModel.ingredients.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;

            ingredients = event.peek();

            if (ingredients == null || ingredients.isEmpty()) viewModel.loadRandomRecipe();
            else viewModel.loadCustomRecipe(ingredients);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.custom:
                withIngredients = true;
                binding.tvTitle.setText(getString(R.string.custom));

                binding.rvRecipes.setAdapter(null);
                binding.rvRecipes.setAdapter(recipeAdapter);

                viewModel.loadCustomRecipe(ingredients);
                return true;

            case R.id.random:
                withIngredients = false;
                binding.tvTitle.setText(getString(R.string.random));

                binding.rvRecipes.setAdapter(null);
                binding.rvRecipes.setAdapter(randomRecipeAdapter);

                viewModel.loadRandomRecipe();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
