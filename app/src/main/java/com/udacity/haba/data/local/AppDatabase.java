package com.udacity.haba.data.local;

import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.udacity.haba.HabaApplication;
import com.udacity.haba.data.local.entity.RecipeDetailsEntity;

@Database(entities = {RecipeDetailsEntity.class}, version = 1, exportSchema = false)
@TypeConverters({RecipeDetailsTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final String DB_NAME = "recipes.db";

    private static volatile AppDatabase instance = null;

    public static synchronized AppDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    HabaApplication.getContext(),
                    AppDatabase.class,
                    DB_NAME
            ).build();
        }

        Log.d(TAG, "Getting the database instance");
        return instance;
    }

    public abstract RecipeDetailsDao recipeDetailsDao();
}
