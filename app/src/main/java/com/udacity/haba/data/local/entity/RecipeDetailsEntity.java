package com.udacity.haba.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "recipes")
public class RecipeDetailsEntity {

    @PrimaryKey
    public final long id;

    @ColumnInfo(name = "title")
    public final String title;

    @ColumnInfo(name = "image")
    public final String image;

    @ColumnInfo(name = "imageType")
    public final String imageType;

    @ColumnInfo(name = "readyInMinutes")
    public final int readyInMinutes;

    @ColumnInfo(name = "servings")
    public final int servings;

    @ColumnInfo(name = "extendedIngredients")
    public final List<DataEntity> extendedIngredients;

    @ColumnInfo(name = "instructions")
    public final String instructions;

    @ColumnInfo(name = "analyzedInstructions")
    public final List<InstructionsEntity> analyzedInstructions;

    @ColumnInfo(name = "aggregateLikes")
    public final int aggregateLikes;

    @ColumnInfo(name = "isLiked")
    public boolean isLiked;

    public RecipeDetailsEntity(long id, String title, String image, String imageType, int readyInMinutes, int servings, List<DataEntity> extendedIngredients, String instructions, List<InstructionsEntity> analyzedInstructions, int aggregateLikes, boolean isLiked) {
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
}
