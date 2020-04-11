package com.udacity.haba.data.model;

import java.io.Serializable;

public class Data implements Serializable {

    private static final long serialversionUID = 43L;

    public final long id;
    public final String name;
    public final String image;

    public Data(long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
