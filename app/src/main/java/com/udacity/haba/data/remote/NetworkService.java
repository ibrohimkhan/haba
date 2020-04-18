package com.udacity.haba.data.remote;

import com.udacity.haba.data.remote.response.RandomRecipeResponse;
import com.udacity.haba.data.remote.response.RecipeDetailsResponse;
import com.udacity.haba.data.remote.response.RecipeResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {

    @GET(Endpoints.RANDOM_RECIPES)
    Single<RandomRecipeResponse> loadRandomRecipes(
            @Query("number") int number,
            @Query("apiKey") String apiKey
    );

    @GET(Endpoints.RECIPES_BY_INGREDIENTS)
    Single<List<RecipeResponse>> loadRecipesByIngredients(
        @Query("ingredients") List<String> ingredients,
        @Query("number") int number,
        @Query("ranking") int ranking,
        @Query("apiKey") String apiKey
    );

    @GET(Endpoints.RECIPE_INFORMATION)
    Single<RecipeDetailsResponse> loadRecipeDetails(
            @Path("recipe_id") long id,
            @Query("apiKey") String apiKey
    );
}