package com.udacity.haba.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udacity.haba.R;
import com.udacity.haba.data.model.RecipeDetails;
import com.udacity.haba.databinding.FragmentFavoriteRecipesBinding;
import com.udacity.haba.ui.eventlistener.RecipeRemoveEventNotifier;
import com.udacity.haba.ui.eventlistener.RecipeSelectionEventListener;
import com.udacity.haba.utils.DialogUtils;

import java.util.List;

public class FavoriteRecipeFragment extends Fragment implements RecipeRemoveEventNotifier {

    public static final String TAG = "FavoriteRecipeFragment";

    private FragmentFavoriteRecipesBinding binding;
    private FavoriteRecipeViewModel viewModel;
    private RecipeSelectionEventListener listener;

    private List<RecipeDetails> recipes;
    private FavoriteAdapter adapter;

    public static FavoriteRecipeFragment newInstance() {
        Bundle args = new Bundle();

        FavoriteRecipeFragment fragment = new FavoriteRecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FavoriteRecipeViewModel.class);

        if (getActivity() != null)
            listener = (RecipeSelectionEventListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.rvRecipes.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new FavoriteAdapter(null, viewModel);
        binding.rvRecipes.setAdapter(adapter);

        viewModel.loading.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;

            if (event.peek()) binding.pbLoader.setVisibility(View.VISIBLE);
            else binding.pbLoader.setVisibility(View.GONE);
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

        viewModel.recipes.observe(getViewLifecycleOwner(), items -> {
            recipes = items;
            adapter.append(items);
        });

        viewModel.onRecipeSelected.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;
            listener.onFavoriteRecipeSelectedEvent(event.peek().intValue(), recipes);
        });
    }

    @Override
    public void notifyRecipeRemoveEvent(RecipeDetails recipeDetails) {
        adapter.remove(recipeDetails);
    }
}
