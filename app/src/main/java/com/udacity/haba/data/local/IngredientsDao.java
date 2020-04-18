package com.udacity.haba.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.udacity.haba.data.local.entity.IngredientEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface IngredientsDao {

    @Query("select * from ingredients order by name asc")
    Single<List<IngredientEntity>> select();

    @Query("select * from ingredients where name like :key || '%' order by name asc")
    Single<List<IngredientEntity>> find(String key);

    @Query("update ingredients set selected = :selected where name = :name")
    Completable update(String name, boolean selected);

    @Insert
    Completable insert(IngredientEntity... recipe);

    @Delete
    Completable delete(IngredientEntity recipe);
}
