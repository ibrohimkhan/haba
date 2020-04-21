package com.udacity.haba.data.repository;

import com.udacity.haba.BuildConfig;
import com.udacity.haba.data.local.AppDatabase;
import com.udacity.haba.data.local.entity.DataEntity;
import com.udacity.haba.data.local.entity.IngredientEntity;
import com.udacity.haba.data.local.entity.InstructionsEntity;
import com.udacity.haba.data.local.entity.RecipeDetailsEntity;
import com.udacity.haba.data.local.entity.StepEntity;
import com.udacity.haba.data.model.Data;
import com.udacity.haba.data.model.Ingredient;
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

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public final class RecipeRepository {

    private static final int MAX_USED_INGREDIENTS = 1;
    private static final int MIN_MISSED_INGREDIENTS = 2;

    private static NetworkService service = Networking.createService(NetworkService.class);
    private static final AppDatabase database = AppDatabase.getInstance();

    private RecipeRepository() {
    }

    public static Single<RandomRecipe> fetchRandomRecipes(int number) {
        return service.loadRandomRecipes(number, BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .map(item -> {
                    List<RecipeDetails> recipeDetails = toRecipeDetailsList(item.recipes);
                    return new RandomRecipe(recipeDetails);
                });
    }

    public static Single<List<Recipe>> fetchRecipesByIngredients(String ingredients, int number) {
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

    public static Completable save(RecipeDetails recipeDetails) {
        return database.recipeDetailsDao()
                .insert(toRecipeDetailsEntity(recipeDetails))
                .subscribeOn(Schedulers.io());
    }

    public static Completable delete(RecipeDetails recipeDetails) {
        return database.recipeDetailsDao()
                .delete(toRecipeDetailsEntity(recipeDetails))
                .subscribeOn(Schedulers.io());
    }

    public static Single<List<RecipeDetails>> selectAll() {
        return database.recipeDetailsDao()
                .select()
                .subscribeOn(Schedulers.io())
                .map(RecipeRepository::entityToRecipeDetailsList);
    }

    public static Completable save(Ingredient ingredient) {
        return database.ingredientsDao()
                .insert(new IngredientEntity(ingredient.getName(), ingredient.isSelected()))
                .subscribeOn(Schedulers.io());
    }

    public static Completable save(IngredientEntity... entities) {
        return database.ingredientsDao()
                .insert(entities)
                .subscribeOn(Schedulers.io());
    }

    public static Completable delete(Ingredient ingredient) {
        return database.ingredientsDao()
                .delete(new IngredientEntity(ingredient.getName(), ingredient.isSelected()))
                .subscribeOn(Schedulers.io());
    }

    public static Single<List<Ingredient>> selectAllIngredients() {
        return database.ingredientsDao()
                .select()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMapIterable(list -> list)
                .map(item -> new Ingredient(item.getName(), item.isSelected()))
                .toList();
    }

    public static Single<List<Ingredient>> findIngredients(String key) {
        return database.ingredientsDao()
                .find(key)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMapIterable(list -> list)
                .map(item -> new Ingredient(item.getName(), item.isSelected()))
                .toList();
    }

    public static Completable update(Ingredient ingredient) {
        return database.ingredientsDao()
                .update(ingredient.getName(), ingredient.isSelected())
                .subscribeOn(Schedulers.io());
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

    private static List<RecipeDetails> entityToRecipeDetailsList(List<RecipeDetailsEntity> entities) {
        List<RecipeDetails> recipeDetails = new ArrayList<>();
        for (RecipeDetailsEntity entity : entities) {
            recipeDetails.add(toRecipeDetails(entity));
        }

        return recipeDetails;
    }

    private static RecipeDetails toRecipeDetails(RecipeDetailsEntity entity) {
        List<Data> extendedIngredients = new ArrayList<>();
        for (DataEntity data : entity.extendedIngredients) {
            extendedIngredients.add(new Data(
                    data.id,
                    data.name,
                    data.image
            ));
        }

        List<Instructions> instructions = new ArrayList<>();
        for (InstructionsEntity instructionsEntity : entity.analyzedInstructions) {

            List<Step> steps = new ArrayList<>();
            for (StepEntity stepEntity : instructionsEntity.steps) {

                List<Data> ingredients = new ArrayList<>();
                for (DataEntity dataEntity : stepEntity.ingredients) {
                    ingredients.add(new Data(
                            dataEntity.id,
                            dataEntity.name,
                            dataEntity.image
                    ));
                }

                List<Data> equipment = new ArrayList<>();
                for (DataEntity dataEntity : stepEntity.equipment) {
                    equipment.add(new Data(
                            dataEntity.id,
                            dataEntity.name,
                            dataEntity.image
                    ));
                }

                steps.add(new Step(
                        stepEntity.number,
                        stepEntity.step,
                        ingredients,
                        equipment
                ));
            }

            Instructions instruction = new Instructions(
                    instructionsEntity.name,
                    steps
            );

            instructions.add(instruction);
        }

        return new RecipeDetails(
                entity.id,
                entity.title,
                entity.image,
                entity.imageType,
                entity.readyInMinutes,
                entity.servings,
                extendedIngredients,
                entity.instructions,
                instructions,
                entity.aggregateLikes,
                entity.isLiked
        );
    }

    private static RecipeDetailsEntity toRecipeDetailsEntity(RecipeDetails recipeDetails) {
        return new RecipeDetailsEntity(
                recipeDetails.id,
                recipeDetails.title,
                recipeDetails.image,
                recipeDetails.imageType,
                recipeDetails.readyInMinutes,
                recipeDetails.servings,
                toDataEntity(recipeDetails.extendedIngredients),
                recipeDetails.instructions,
                toInstructionsEntity(recipeDetails.analyzedInstructions),
                recipeDetails.aggregateLikes,
                recipeDetails.isLiked
        );
    }

    private static List<DataEntity> toDataEntity(List<Data> data) {
        List<DataEntity> entitie = new ArrayList<>();
        for (Data item : data) {
            entitie.add(new DataEntity(
                    item.id,
                    item.name,
                    item.image
            ));
        }

        return entitie;
    }

    private static List<InstructionsEntity> toInstructionsEntity(List<Instructions> items) {
        List<InstructionsEntity> entities = new ArrayList<>();
        for (Instructions item : items) {
            entities.add(new InstructionsEntity(
                    item.name,
                    toStepEntity(item.steps)
            ));
        }

        return entities;
    }

    private static List<StepEntity> toStepEntity(List<Step> items) {
        List<StepEntity> entities = new ArrayList<>();
        for (Step item : items) {
            entities.add(new StepEntity(
                    item.number,
                    item.step,
                    toDataEntity(item.ingredients),
                    toDataEntity(item.equipment)
            ));
        }

        return entities;
    }
}
