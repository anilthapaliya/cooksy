package com.bca.cooksy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meals {

    @SerializedName("meals")
    List<Recipe> meals;

    public List<Recipe> getMeals() {
        return meals;
    }

    public void setMeals(List<Recipe> meals) {
        this.meals = meals;
    }

}
