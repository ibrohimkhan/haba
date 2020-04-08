package com.udacity.haba.data.local.entity;

import java.util.List;

public class InstructionsEntity {

    public final String name;
    public final List<StepEntity> steps;

    public InstructionsEntity(String name, List<StepEntity> steps) {
        this.name = name;
        this.steps = steps;
    }
}
