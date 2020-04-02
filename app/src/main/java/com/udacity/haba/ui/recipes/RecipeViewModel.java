package com.udacity.haba.ui.recipes;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.udacity.haba.data.model.RandomRecipe;
import com.udacity.haba.data.model.Recipe;
import com.udacity.haba.data.repository.RecipeRepository;
import com.udacity.haba.utils.Event;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class RecipeViewModel extends ViewModel {

    private static final String TAG         = RecipeViewModel.class.getSimpleName();
    private static final int MAX_RECIPES    = 25;

    private CompositeDisposable disposable  = new CompositeDisposable();

    MutableLiveData<Event<Boolean>> loading             = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> error               = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> connectionError     = new MutableLiveData<>();
    MutableLiveData<Event<String>> errorMessage         = new MutableLiveData<>();
    MutableLiveData<Event<RandomRecipe>> randomRecipes  = new MutableLiveData<>();
    MutableLiveData<List<Recipe>> recipes               = new MutableLiveData<>();
    MutableLiveData<Event<Long>> onRecipeSelected       = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public RecipeViewModel() {
        loadRecipes();
    }

    public void reload() {
        if (loading.getValue().peek()) return;
        loadRecipes();
    }

    private void loadRecipes() {
        loading.postValue(new Event<>(true));
        disposable.add(
                RecipeRepository.fetchRandomRecipes(MAX_RECIPES)
                        .subscribeOn(Schedulers.io())
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

    private void notifyUI(RandomRecipe recipes) {
        loading.postValue(new Event<>(false));
        randomRecipes.postValue(new Event<>(recipes));
    }
}
