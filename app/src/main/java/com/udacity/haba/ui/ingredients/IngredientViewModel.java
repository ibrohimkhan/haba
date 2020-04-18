package com.udacity.haba.ui.ingredients;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.udacity.haba.data.model.Ingredient;
import com.udacity.haba.data.repository.RecipeRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class IngredientViewModel extends ViewModel {

    private static final String TAG = IngredientViewModel.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();

    MutableLiveData<Boolean> error = new MutableLiveData<>();
    MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();
    MutableLiveData<List<Ingredient>> filtered = new MutableLiveData<>();
    MutableLiveData<Boolean> completed = new MutableLiveData<>();

    public IngredientViewModel() {
        loadIngredients();
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public void reload() {
        loadIngredients();
    }

    public void save(Ingredient ingredient) {
        disposable.add(
                RecipeRepository.save(ingredient)
                        .subscribe(
                                () -> {
                                    completed.postValue(true);
                                }
                        )
        );
    }

    public void delete(Ingredient ingredient) {
        disposable.add(
                RecipeRepository.delete(ingredient)
                        .subscribe(
                                () -> {
                                    completed.postValue(true);
                                }
                        )
        );
    }

    public void update(Ingredient ingredient) {
        if (ingredient == null) return;

        disposable.add(
                RecipeRepository.update(ingredient)
                        .subscribe()
        );
    }

    public void filter(CharSequence key) {
        if (TextUtils.isEmpty(key)) {
            disposable.add(
                    RecipeRepository.selectAllIngredients()
                            .subscribe(
                                    result -> {
                                        filtered.postValue(result);
                                    },
                                    throwable -> {
                                        error.postValue(true);
                                        handleError(throwable);
                                    }
                            )
            );

        } else {
            disposable.add(
                    RecipeRepository.findIngredients(key.toString())
                            .subscribe(
                                    result -> {
                                        filtered.postValue(result);
                                    },
                                    throwable -> {
                                        error.postValue(true);
                                        handleError(throwable);
                                    }
                            )
            );
        }
    }

    private void loadIngredients() {
        disposable.add(
                RecipeRepository.selectAllIngredients()
                        .subscribe(
                                result -> {
                                    ingredients.postValue(result);
                                },
                                throwable -> {
                                    error.postValue(true);
                                    handleError(throwable);
                                }
                        )
        );
    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, throwable.toString());

        FirebaseCrashlytics.getInstance().recordException(throwable);
        FirebaseCrashlytics.getInstance().setCustomKey(TAG, throwable.getMessage());
        FirebaseCrashlytics.getInstance().log(throwable.toString());
    }
}
