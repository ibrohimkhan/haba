package com.udacity.haba.ui.recipedetails;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.udacity.haba.data.model.RecipeDetails;
import com.udacity.haba.data.repository.RecipeRepository;
import com.udacity.haba.ui.recipes.RecipeViewModel;
import com.udacity.haba.utils.Event;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class RecipeDetailsViewModel extends ViewModel {

    private static final String TAG         = RecipeViewModel.class.getSimpleName();

    private CompositeDisposable disposable  = new CompositeDisposable();
    private static Map<Long, RecipeDetails> cache  = new HashMap<>();

    MutableLiveData<Event<Boolean>> loading             = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> error               = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> connectionError     = new MutableLiveData<>();
    MutableLiveData<Event<String>> errorMessage         = new MutableLiveData<>();
    MutableLiveData<RecipeDetails> recipeDetails        = new MutableLiveData<>();

    MutableLiveData<Event<Boolean>> completed           = new MutableLiveData<>();

    private long currentId;

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public void initialize(long id) {
        if (!cache.containsKey(id)) {
            currentId = id;
            loadRecipeDetails(id);

        } else {
            recipeDetails.setValue(cache.get(id));
        }
    }

    public void save(RecipeDetails recipeDetails) {
        disposable.add(
                RecipeRepository.save(recipeDetails)
                        .subscribe(
                                () -> {
                                    completed.postValue(new Event<>(true));
                                },
                                throwable -> {
                                    Log.d(TAG, throwable.toString());
                                    FirebaseCrashlytics.getInstance().recordException(throwable);
                                    FirebaseCrashlytics.getInstance().setCustomKey(TAG, throwable.toString());
                                }
                        )
        );
    }

    public void remove(RecipeDetails recipeDetails) {
        disposable.add(
                RecipeRepository.delete(recipeDetails)
                        .subscribe(
                                () -> {
                                    completed.postValue(new Event<>(true));
                                },
                                throwable -> {
                                    Log.d(TAG, throwable.toString());
                                    FirebaseCrashlytics.getInstance().recordException(throwable);
                                    FirebaseCrashlytics.getInstance().setCustomKey(TAG, throwable.toString());
                                }
                        )
        );
    }

    private void loadRecipeDetails(long id) {
        loading.setValue(new Event<>(true));

        disposable.add(
                RecipeRepository.fetchRecipeDetails(id)
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::notifyUI, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        loading.postValue(new Event<>(false));

        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            String message = null;

            try {
                if (exception.code() != 401) {
                    message = exception.response().errorBody().string();

                } else {
                    message =
                            exception
                                    .response()
                                    .errorBody()
                                    .string()
                                    .split(",")[2]
                                    .split(":")[1]
                                    .replace("}", "")
                                    .replaceAll("\"", "");
                }

                int start = message.indexOf("We");
                int end = message.indexOf(" If this");

                message = message.substring(start, end);

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

    private void notifyUI(RecipeDetails recipeDetails) {
        cache.put(currentId, recipeDetails);

        loading.postValue(new Event<>(false));
        this.recipeDetails.postValue(recipeDetails);
    }
}
