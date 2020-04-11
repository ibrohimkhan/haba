package com.udacity.haba.ui.favorites;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.udacity.haba.data.model.RecipeDetails;
import com.udacity.haba.data.repository.RecipeRepository;
import com.udacity.haba.utils.Event;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class FavoriteRecipeViewModel extends ViewModel {

    private static final String TAG                  = FavoriteRecipeViewModel.class.getSimpleName();
    private CompositeDisposable disposable           = new CompositeDisposable();

    MutableLiveData<Event<Boolean>> loading          = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> error            = new MutableLiveData<>();
    MutableLiveData<List<RecipeDetails>> recipes     = new MutableLiveData<>();
    MutableLiveData<Event<Integer>> onRecipeSelected = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public FavoriteRecipeViewModel() {
        loadRecipes();
    }

    private void loadRecipes() {
        loading.setValue(new Event<>(true));
        disposable.add(
                RecipeRepository.selectAll()
                        .subscribe(
                                items -> {
                                    loading.postValue(new Event<>(false));
                                    recipes.postValue(items);
                                },
                                throwable -> {
                                    loading.postValue(new Event<>(false));
                                    error.postValue(new Event<>(true));

                                    Log.d(TAG, throwable.toString());

                                    FirebaseCrashlytics.getInstance().recordException(throwable);
                                    FirebaseCrashlytics.getInstance().setCustomKey(TAG, throwable.toString());
                                    FirebaseCrashlytics.getInstance().log(throwable.toString());
                                }
                        )
        );
    }
}
