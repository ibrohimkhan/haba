package com.udacity.haba.data.model;

import java.util.List;

public class Step {

    public final int number;
    public final String step;
    public final List<Data> ingredients;
    public final List<Data> equipment;

    public Step(int number, String step, List<Data> ingredients, List<Data> equipment) {
        this.number = number;
        this.step = step;
        this.ingredients = ingredients;
        this.equipment = equipment;
    }
}
