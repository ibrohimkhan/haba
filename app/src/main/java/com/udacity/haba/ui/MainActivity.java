package com.udacity.haba.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.udacity.haba.R;
import com.udacity.haba.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String ACTIVE_FRAGMENT = "active_fragment";
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupBottomNavigationBar();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

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

    private void setupBottomNavigationBar() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.recipes:
                    Toast.makeText(this, "Recipes selected", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.ingredients:
                    Toast.makeText(this, "Ingredients selected", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.favorites:
                    Toast.makeText(this, "Favorites selected", Toast.LENGTH_SHORT).show();
                    return true;
            }

            return false;
        });

        binding.bottomNavigation.setSelectedItemId(R.id.ingredients);
    }
}
