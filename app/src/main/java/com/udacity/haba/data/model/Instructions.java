package com.udacity.haba.data.model;

import java.io.Serializable;
import java.util.List;

public class Instructions implements Serializable {

    private static final long serialversionUID = 44L;

    public final String name;
    public final List<Step> steps;

    public Instructions(String name, List<Step> steps) {
        this.name = name;
        this.steps = steps;
    }
}
