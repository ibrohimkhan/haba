package com.udacity.haba.data.model;

import java.io.Serializable;

public class Recipe implements Serializable {

    private static final long serialversionUID = 41L;

    public final long id;
    public final String title;
    public final String image;
    public final long likes;

    public Recipe(long id, String title, String image, long likes) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != recipe.id) return false;
        if (likes != recipe.likes) return false;
        if (!title.equals(recipe.title)) return false;
        return image != null ? image.equals(recipe.image) : recipe.image == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (int) (likes ^ (likes >>> 32));
        return result;
    }
}
