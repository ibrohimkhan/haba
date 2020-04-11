package com.udacity.haba.data.model;

import java.io.Serializable;
import java.util.List;

public class RecipeDetails extends Recipe implements Serializable {

    private static final long serialversionUID = 42L;

    public final long id;
    public final String title;
    public final String image;
    public final String imageType;
    public final int readyInMinutes;
    public final int servings;
    public final List<Data> extendedIngredients;
    public final String instructions;
    public final List<Instructions> analyzedInstructions;
    public final int aggregateLikes;
    public boolean isLiked;

    public RecipeDetails(long id, String title, String image, String imageType, int readyInMinutes, int servings, List<Data> extendedIngredients, String instructions, List<Instructions> analyzedInstructions, int aggregateLikes) {
        super(id, title, image, aggregateLikes);
        this.id = id;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.extendedIngredients = extendedIngredients;
        this.instructions = instructions;
        this.analyzedInstructions = analyzedInstructions;
        this.aggregateLikes = aggregateLikes;
    }

    public RecipeDetails(long id, String title, String image, String imageType, int readyInMinutes, int servings, List<Data> extendedIngredients, String instructions, List<Instructions> analyzedInstructions, int aggregateLikes, boolean isLiked) {
        super(id, title, image, aggregateLikes);
        this.id = id;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.extendedIngredients = extendedIngredients;
        this.instructions = instructions;
        this.analyzedInstructions = analyzedInstructions;
        this.aggregateLikes = aggregateLikes;
        this.isLiked = isLiked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RecipeDetails details = (RecipeDetails) o;

        if (id != details.id) return false;
        if (readyInMinutes != details.readyInMinutes) return false;
        if (servings != details.servings) return false;
        if (aggregateLikes != details.aggregateLikes) return false;
        if (!title.equals(details.title)) return false;
        if (image != null ? !image.equals(details.image) : details.image != null) return false;
        if (imageType != null ? !imageType.equals(details.imageType) : details.imageType != null)
            return false;
        if (extendedIngredients != null ? !extendedIngredients.equals(details.extendedIngredients) : details.extendedIngredients != null)
            return false;
        if (instructions != null ? !instructions.equals(details.instructions) : details.instructions != null)
            return false;
        return analyzedInstructions != null ? analyzedInstructions.equals(details.analyzedInstructions) : details.analyzedInstructions == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (imageType != null ? imageType.hashCode() : 0);
        result = 31 * result + readyInMinutes;
        result = 31 * result + servings;
        result = 31 * result + (extendedIngredients != null ? extendedIngredients.hashCode() : 0);
        result = 31 * result + (instructions != null ? instructions.hashCode() : 0);
        result = 31 * result + (analyzedInstructions != null ? analyzedInstructions.hashCode() : 0);
        result = 31 * result + aggregateLikes;
        return result;
    }
}
