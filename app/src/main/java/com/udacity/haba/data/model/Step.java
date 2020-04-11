package com.udacity.haba.data.model;

import java.io.Serializable;
import java.util.List;

public class Step implements Serializable {

    private static final long serialversionUID = 45L;

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
