package com.udacity.haba.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.haba.data.local.entity.DataEntity;
import com.udacity.haba.data.local.entity.InstructionsEntity;
import com.udacity.haba.data.local.entity.StepEntity;

import java.util.Collections;
import java.util.List;

public final class RecipeDetailsTypeConverter {

    private RecipeDetailsTypeConverter() {}

    @TypeConverter
    public static String fromListOfData(List<DataEntity> values) {
        if (values == null || values.isEmpty()) return null;
        return new Gson().toJson(values);
    }

    @TypeConverter
    public static List<DataEntity> toListOfData(String value) {
        if (value == null || value == "") return Collections.emptyList();
        return new Gson().fromJson(value, new TypeToken<List<DataEntity>>(){}.getType());
    }

    @TypeConverter
    public static String fromListOfInstructions(List<InstructionsEntity> values) {
        if (values == null || values.isEmpty()) return null;
        return new Gson().toJson(values);
    }

    @TypeConverter
    public static List<InstructionsEntity> toListOfInstructions(String value) {
        if (value == null || value == "") return Collections.emptyList();
        return new Gson().fromJson(value, new TypeToken<List<InstructionsEntity>>(){}.getType());
    }

    @TypeConverter
    public static String fromListOfSteps(List<StepEntity> values) {
        if (values == null || values.isEmpty()) return null;
        return new Gson().toJson(values);
    }

    @TypeConverter
    public static List<StepEntity> toListOfSteps(String value) {
        if (value == null || value == "") return Collections.emptyList();
        return new Gson().fromJson(value, new TypeToken<List<StepEntity>>(){}.getType());
    }
}
