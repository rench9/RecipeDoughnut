package com.r4hu7.recipedoughnut.data.remote.response.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.r4hu7.recipedoughnut.data.local.RecipeTypeConverter;

import java.util.ArrayList;

@TypeConverters(RecipeTypeConverter.class)
@Entity(tableName = Recipe.TABLE_NAME)
public class Recipe {
    public static final String TABLE_NAME = "_recipe";
    @PrimaryKey
    private int id;

    private String name;

    private ArrayList<Ingredient> ingredients;

    private ArrayList<Step> steps;

    private int servings;

    private String image;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return this.steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return this.servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
