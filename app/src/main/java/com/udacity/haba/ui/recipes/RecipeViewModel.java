package com.udacity.haba.ui.recipes;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.udacity.haba.data.model.Ingredient;
import com.udacity.haba.data.model.RandomRecipe;
import com.udacity.haba.data.model.Recipe;
import com.udacity.haba.data.repository.RecipeRepository;
import com.udacity.haba.utils.Event;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class RecipeViewModel extends ViewModel {

    private static final String TAG                = RecipeViewModel.class.getSimpleName();
    private static final int MAX_RECIPES           = 25;
    private static final int MAX_CUSTOM_RECIPES    = 100;

    private CompositeDisposable disposable  = new CompositeDisposable();

    MutableLiveData<Event<Boolean>> loading                    = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> error                      = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> connectionError            = new MutableLiveData<>();
    MutableLiveData<Event<String>> errorMessage                = new MutableLiveData<>();
    MutableLiveData<Event<RandomRecipe>> randomRecipes         = new MutableLiveData<>();
    MutableLiveData<Event<List<Recipe>>> recipes               = new MutableLiveData<>();
    MutableLiveData<Event<Integer>> onRecipeSelected           = new MutableLiveData<>();
    MutableLiveData<Event<List<Ingredient>>> ingredients       = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public RecipeViewModel() {
        loadIngredients();
    }

    public void loadRandomRecipe() {
        if (loading.getValue().peek()) return;
        loadRecipes();
    }

    public void loadCustomRecipe(List<Ingredient> ingredients) {
        if (loading.getValue().peek()) return;

        List<String> names = new ArrayList<>();
        for (Ingredient item : ingredients) {
            if (!item.isSelected()) continue;

            if (!names.contains(item.getName()))
                names.add(item.getName());
        }

        loadRecipes(names);
    }

    public List<Long> getRecipeIds(List<? extends Recipe> recipes) {
        List<Long> ids = new ArrayList<>();
        for (Recipe recipe : recipes) ids.add(recipe.id);

        return ids;
    }

    private void loadIngredients() {
        loading.postValue(new Event<>(true));

        disposable.add(
                RecipeRepository.selectAllIngredients()
                        .subscribe(
                                result -> {
                                    loading.postValue(new Event<>(false));
                                    ingredients.postValue(new Event<>(result));
                                },
                                throwable -> {
                                    loading.postValue(new Event<>(false));
                                    error.postValue(new Event<>(true));
                                    handleError(throwable);
                                }
                        )
        );
    }

    private void loadRecipes() {
        loading.postValue(new Event<>(true));
        disposable.add(
                RecipeRepository.fetchRandomRecipes(MAX_RECIPES)
                        .subscribe(this::notifyUI, this::handleNetworkError)
        );
    }

    private void loadRecipes(List<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) return;
        String items = ingredients.toString()
                .substring(1, ingredients.toString().length() - 1)
                .toLowerCase();

        loading.postValue(new Event<>(true));

        disposable.add(
                RecipeRepository.fetchRecipesByIngredients(items, MAX_CUSTOM_RECIPES)
                        .subscribe(this::notifyUI, this::handleNetworkError)
        );
    }

    private void handleNetworkError(Throwable throwable) {
        loading.postValue(new Event<>(false));

        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            String message = null;

            try {
                message =
                        exception
                                .response()
                                .errorBody()
                                .string()
                                .split(",")[2]
                                .split(":")[1]
                                .replace("}", "")
                                .replaceAll("\"", "");

            } catch (Exception e) {
                Log.d(TAG, e.toString());
                FirebaseCrashlytics.getInstance().recordException(e);
                FirebaseCrashlytics.getInstance().setCustomKey(TAG, e.getMessage());
            }

            if (message == null)
                message = exception.message();

            errorMessage.postValue(new Event<>(message));

            FirebaseCrashlytics.getInstance().recordException(exception);
            FirebaseCrashlytics.getInstance().setCustomKey(TAG, message);
            FirebaseCrashlytics.getInstance().log(message);

            Log.d(TAG, message);

        } else if (throwable instanceof UnknownHostException) {
            connectionError.postValue(new Event<>(true));
            Log.d(TAG, throwable.toString());

        } else {
            error.postValue(new Event<>(true));
            FirebaseCrashlytics.getInstance().recordException(throwable);
            FirebaseCrashlytics.getInstance().setCustomKey(TAG, throwable.getMessage());
            Log.d(TAG, throwable.toString());
        }
    }

    private void handleError(Throwable throwable) {
        Log.d(TAG, throwable.toString());

        FirebaseCrashlytics.getInstance().recordException(throwable);
        FirebaseCrashlytics.getInstance().setCustomKey(TAG, throwable.getMessage());
        FirebaseCrashlytics.getInstance().log(throwable.toString());
    }

    private void notifyUI(RandomRecipe recipes) {
        loading.postValue(new Event<>(false));
        randomRecipes.postValue(new Event<>(recipes));
    }

    private void notifyUI(List<Recipe> recipes) {
        loading.postValue(new Event<>(false));
        this.recipes.postValue(new Event<>(recipes));
    }
}
