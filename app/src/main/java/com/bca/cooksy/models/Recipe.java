package com.bca.cooksy.models;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("strMeal")
    String title;

    @SerializedName("strArea")
    String origin;

    @SerializedName("strCategory")
    String category;

    @SerializedName("strMealThumb")
    String imageLink;

    @SerializedName("strInstructions")
    String instructions;

    public Recipe() {}

    public Recipe(String title, String origin, String category) {
        this.title = title;
        this.origin = origin;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}
