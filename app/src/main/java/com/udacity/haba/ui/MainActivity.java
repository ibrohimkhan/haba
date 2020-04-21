package com.udacity.haba.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.udacity.haba.R;
import com.udacity.haba.data.model.RecipeDetails;
import com.udacity.haba.databinding.ActivityMainBinding;
import com.udacity.haba.ui.eventlistener.RecipeRemoveEventListener;
import com.udacity.haba.ui.eventlistener.RecipeRemoveEventNotifier;
import com.udacity.haba.ui.eventlistener.RecipeSelectionEventListener;
import com.udacity.haba.ui.favorites.FavoriteDetailsViewPagerFragment;
import com.udacity.haba.ui.favorites.FavoriteRecipeFragment;
import com.udacity.haba.ui.ingredients.IngredientFragment;
import com.udacity.haba.ui.recipedetails.RecipeDetailsViewPagerFragment;
import com.udacity.haba.ui.recipes.RecipeFragment;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeSelectionEventListener, RecipeRemoveEventListener {

    private ActivityMainBinding binding;

    private static final String ACTIVE_FRAGMENT = "active_fragment";
    private static final String RECIPE_POSITION = "recipe_position";
    private static final String RECIPE_IDS = "recipe_ids";
    private static final String RECIPE_DETAILS = "recipe_details";

    private Fragment activeFragment;
    private int recipePosition;
    private List<Long> recipeIds;
    private List<RecipeDetails> recipeDetails;

    private RecipeRemoveEventNotifier notifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            String tag = savedInstanceState.getString(ACTIVE_FRAGMENT);
            activeFragment = getSupportFragmentManager().findFragmentByTag(tag);

            if (activeFragment instanceof RecipeFragment)
                showRecipesFragment();

            else if (activeFragment instanceof FavoriteRecipeFragment)
                showFavoriteRecipeFragment();

            else if (activeFragment instanceof RecipeDetailsViewPagerFragment) {
                if (savedInstanceState.containsKey(RECIPE_POSITION)) {
                    recipePosition = savedInstanceState.getInt(RECIPE_POSITION);

                    if (savedInstanceState.containsKey(RECIPE_IDS))
                        recipeIds = (List<Long>) savedInstanceState.getSerializable(RECIPE_IDS);

                    if (savedInstanceState.containsKey(RECIPE_DETAILS))
                        recipeDetails = (List<RecipeDetails>) savedInstanceState.getSerializable(RECIPE_DETAILS);

                    if (recipeDetails == null) showRecipeDetailsFragment(recipePosition, recipeIds);
                    else showFavoriteRecipeDetailsFragment(recipePosition, recipeDetails);
                }
            }

        } else {
            setupBottomNavigationBar();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

            getSupportFragmentManager().popBackStackImmediate();

            int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
            String tag = getSupportFragmentManager().getBackStackEntryAt(index).getName();

            activeFragment = getSupportFragmentManager().findFragmentByTag(tag);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACTIVE_FRAGMENT, activeFragment.getTag());
        outState.putInt(RECIPE_POSITION, recipePosition);
        outState.putSerializable(RECIPE_IDS, (Serializable) recipeIds);
        outState.putSerializable(RECIPE_DETAILS, (Serializable) recipeDetails);
    }

    @Override
    public void onRecipeSelectedEvent(int position, List<Long> recipeIds) {
        recipePosition = position;
        this.recipeIds = recipeIds;

        recipeDetails = null;
        showRecipeDetailsFragment(position, recipeIds);
    }

    @Override
    public void onFavoriteRecipeSelectedEvent(int position, List<RecipeDetails> recipeDetails) {
        recipePosition = position;
        this.recipeDetails = recipeDetails;

        recipeIds = null;
        showFavoriteRecipeDetailsFragment(position, recipeDetails);
    }

    @Override
    public void onRecipeRemoveEvent(RecipeDetails recipeDetails) {
        if (notifier != null)
            notifier.notifyRecipeRemoveEvent(recipeDetails);
    }

    private void setupBottomNavigationBar() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.recipes:
                    clearBackstack();
                    showRecipesFragment();
                    return true;

                case R.id.ingredients:
                    clearBackstack();
                    showIngredientsFragment();
                    return true;

                case R.id.favorites:
                    clearBackstack();
                    showFavoriteRecipeFragment();
                    return true;
            }

            return false;
        });

        binding.bottomNavigation.setSelectedItemId(R.id.ingredients);
    }

    private void showRecipesFragment() {
        if (activeFragment instanceof RecipeFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RecipeFragment.TAG);

        if (fragment == null) {
            fragment = RecipeFragment.newInstance();
            addNewFragment(fragment, RecipeFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void showRecipeDetailsFragment(int position, List<Long> recipeIds) {
        if (activeFragment instanceof RecipeDetailsViewPagerFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RecipeDetailsViewPagerFragment.TAG);

        if (fragment == null) {
            fragment = RecipeDetailsViewPagerFragment.newInstance(position, recipeIds);
            addNewFragment(fragment, RecipeDetailsViewPagerFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void showFavoriteRecipeDetailsFragment(int position, List<RecipeDetails> recipeDetails) {
        if (activeFragment instanceof FavoriteDetailsViewPagerFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FavoriteDetailsViewPagerFragment.TAG);

        if (fragment == null) {
            fragment = FavoriteDetailsViewPagerFragment.newInstance(position, recipeDetails);
            addNewFragment(fragment, FavoriteDetailsViewPagerFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void showIngredientsFragment() {
        if (activeFragment instanceof IngredientFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(IngredientFragment.TAG);

        if (fragment == null) {
            fragment = IngredientFragment.newInstance();
            addNewFragment(fragment, IngredientFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void showFavoriteRecipeFragment() {
        if (activeFragment instanceof FavoriteRecipeFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FavoriteRecipeFragment.TAG);

        if (fragment == null) {
            fragment = FavoriteRecipeFragment.newInstance();
            addNewFragment(fragment, FavoriteRecipeFragment.TAG);

        } else {
            showFragment(fragment);
        }

        notifier = (RecipeRemoveEventNotifier) fragment;
    }

    private void addNewFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (activeFragment != null) transaction.hide(activeFragment);

        transaction.add(R.id.container_fragment, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

        activeFragment = fragment;
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (activeFragment != null)
            transaction.hide(activeFragment);

        transaction.show(fragment);
        transaction.commitNow();

        activeFragment = fragment;
    }

    private void clearBackstack() {
        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }

        activeFragment = null;
    }
}
