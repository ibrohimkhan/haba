package com.udacity.haba.data.model;

import java.util.List;

public class Instructions {

    public final String name;
    public final List<Step> steps;

    public Instructions(String name, List<Step> steps) {
        this.name = name;
        this.steps = steps;
    }
}
