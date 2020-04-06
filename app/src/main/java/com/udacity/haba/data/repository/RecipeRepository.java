package com.udacity.haba.data.repository;

import com.udacity.haba.BuildConfig;
import com.udacity.haba.data.model.Data;
import com.udacity.haba.data.model.Instructions;
import com.udacity.haba.data.model.RandomRecipe;
import com.udacity.haba.data.model.Recipe;
import com.udacity.haba.data.model.RecipeDetails;
import com.udacity.haba.data.model.Step;
import com.udacity.haba.data.remote.NetworkService;
import com.udacity.haba.data.remote.Networking;
import com.udacity.haba.data.remote.response.ExtendedIngredientsResponse;
import com.udacity.haba.data.remote.response.InstructionsResponse;
import com.udacity.haba.data.remote.response.RecipeDetailsResponse;
import com.udacity.haba.data.remote.response.RecipeResponse;
import com.udacity.haba.data.remote.response.StepDataResponse;
import com.udacity.haba.data.remote.response.StepResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public final class RecipeRepository {

    private static final int MAX_USED_INGREDIENTS = 1;
    private static final int MIN_MISSED_INGREDIENTS = 2;

    private static NetworkService service = Networking.createService(NetworkService.class);

    private RecipeRepository() {}

    public static Single<RandomRecipe> fetchRandomRecipes(int number) {
        return service.loadRandomRecipes(number, BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(item -> {
                    List<RecipeDetails> recipeDetails = toRecipeDetailsList(item.recipes);
                    return new RandomRecipe(recipeDetails);
                });
    }

    public static Single<List<Recipe>> fetchRecipesByIngredients(List<String> ingredients, int number) {
        return service.loadRecipesByIngredients(ingredients, number, MIN_MISSED_INGREDIENTS, BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(items -> {
                    List<Recipe> recipes = new ArrayList<>();
                    for (RecipeResponse recipeResponse : items) {
                        recipes.add(new Recipe(
                                recipeResponse.id,
                                recipeResponse.title,
                                recipeResponse.image,
                                recipeResponse.likes
                        ));
                    }

                    return recipes;
                });
    }

    public static Single<RecipeDetails> fetchRecipeDetails(long id) {
        return service.loadRecipeDetails(id, BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(RecipeRepository::toRecipeDetails);
    }

    public static void save(RecipeDetails recipeDetails) {
        // TODO: SAVE RECIPE INTO DB
    }

    private static List<RecipeDetails> toRecipeDetailsList(List<RecipeDetailsResponse> responses) {
        List<RecipeDetails> recipeDetails = new ArrayList<>();
        for (RecipeDetailsResponse response : responses) {
            RecipeDetails details = toRecipeDetails(response);
            recipeDetails.add(details);
        }

        return recipeDetails;
    }

    private static RecipeDetails toRecipeDetails(RecipeDetailsResponse response) {
        List<Data> extendedIngredients = new ArrayList<>();
        for (ExtendedIngredientsResponse ingredient : response.extendedIngredients) {
            Data data = new Data(
                    ingredient.id,
                    ingredient.name,
                    ingredient.image
            );

            extendedIngredients.add(data);
        }

        List<Instructions> instructions = new ArrayList<>();
        for (InstructionsResponse instructionsResponse : response.analyzedInstructions) {

            List<Step> steps = new ArrayList<>();
            for (StepResponse stepResponse : instructionsResponse.steps) {

                List<Data> ingredients = new ArrayList<>();
                for (StepDataResponse stepDataResponse : stepResponse.ingredients) {
                    ingredients.add(new Data(
                            stepDataResponse.id,
                            stepDataResponse.name,
                            stepDataResponse.image
                    ));
                }

                List<Data> equipment = new ArrayList<>();
                for (StepDataResponse stepDataResponse : stepResponse.equipment) {
                    equipment.add(new Data(
                            stepDataResponse.id,
                            stepDataResponse.name,
                            stepDataResponse.image
                    ));
                }

                steps.add(new Step(
                        stepResponse.number,
                        stepResponse.step,
                        ingredients,
                        equipment
                ));
            }

            Instructions instruction = new Instructions(
                    instructionsResponse.name,
                    steps
            );

            instructions.add(instruction);
        }

        return new RecipeDetails(
                response.id,
                response.title,
                response.image,
                response.imageType,
                response.readyInMinutes,
                response.servings,
                extendedIngredients,
                response.instructions,
                instructions,
                response.aggregateLikes
        );
    }
}
