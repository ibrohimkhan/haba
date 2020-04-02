package com.udacity.haba.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.haba.R;
import com.udacity.haba.databinding.FragmentRecipeBinding;
import com.udacity.haba.utils.DialogUtils;

public class RecipeFragment extends Fragment {

    public static final String TAG = "RecipeFragment";
    private static final String REFRESH_KEY = "refreshing_key";

    private FragmentRecipeBinding binding;
    private RecipeViewModel viewModel;
    private RecipeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private boolean scrolling;
    private int rvPosition;

    public static RecipeFragment newInstance() {
        Bundle args = new Bundle();

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null)
            viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(REFRESH_KEY, binding.srSwipeToRefresh.isRefreshing());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.rvRecipes.setLayoutManager(linearLayoutManager);

        binding.rvRecipes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (linearLayoutManager.getItemCount() == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    scrolling = true;
                    rvPosition = linearLayoutManager.findLastVisibleItemPosition();
                    viewModel.reload();
                }
            }
        });

        binding.srSwipeToRefresh.setProgressViewEndTarget(false, 0);

        if (savedInstanceState != null && savedInstanceState.containsKey(REFRESH_KEY)) {
            boolean isRefreshing = savedInstanceState.getBoolean(REFRESH_KEY);
            binding.srSwipeToRefresh.setRefreshing(isRefreshing);
        }

        binding.srSwipeToRefresh.setOnRefreshListener(() -> {
            viewModel.reload();
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

            if (adapter == null) {
                adapter = new RecipeAdapter(event.peek().recipes, viewModel);
                binding.rvRecipes.setAdapter(adapter);

            } else if (scrolling) {
                scrolling = false;
                adapter.append(event.peek().recipes);
                binding.rvRecipes.scrollToPosition(rvPosition);

            } else  {
                adapter.updateRecipes(event.peek().recipes);
            }
        });

        viewModel.onRecipeSelected.observe(getViewLifecycleOwner(), event -> {
            if (event.getIfNotHandled() == null) return;
            // TODO: pass this id to RecipeDetails fragment
        });
    }
}
