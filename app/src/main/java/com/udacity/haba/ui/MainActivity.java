package com.udacity.haba.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udacity.haba.R;
import com.udacity.haba.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
