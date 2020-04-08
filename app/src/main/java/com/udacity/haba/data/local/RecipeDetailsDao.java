package com.udacity.haba.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.udacity.haba.data.local.entity.RecipeDetailsEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface RecipeDetailsDao {

    @Query("select * from recipes")
    Single<List<RecipeDetailsEntity>> select();

    @Insert
    Completable insert(RecipeDetailsEntity... recipe);

    @Delete
    Completable delete(RecipeDetailsEntity recipe);
}
