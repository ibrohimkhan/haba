package com.udacity.haba.data.local.entity;

import java.util.List;

public class StepEntity {

    public final int number;
    public final String step;
    public final List<DataEntity> ingredients;
    public final List<DataEntity> equipment;

    public StepEntity(int number, String step, List<DataEntity> ingredients, List<DataEntity> equipment) {
        this.number = number;
        this.step = step;
        this.ingredients = ingredients;
        this.equipment = equipment;
    }
}
