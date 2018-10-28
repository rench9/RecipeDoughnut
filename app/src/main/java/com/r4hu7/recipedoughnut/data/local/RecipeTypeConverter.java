package com.r4hu7.recipedoughnut.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r4hu7.recipedoughnut.data.remote.response.model.Ingredient;
import com.r4hu7.recipedoughnut.data.remote.response.model.Step;

import java.util.ArrayList;

public class RecipeTypeConverter {
    @TypeConverter
    public static ArrayList<Ingredient> stringToIngredients(String s) {
        return new Gson().fromJson(s,
                new TypeToken<ArrayList<Ingredient>>() {
                }.getType());
    }

    @TypeConverter
    public static String ingredientsToString(ArrayList<Ingredient> ingredients) {
        return new Gson().toJson(ingredients);
    }


    @TypeConverter
    public static ArrayList<Step> stringToSteps(String s) {
        return new Gson().fromJson(s,
                new TypeToken<ArrayList<Step>>() {
                }.getType());
    }

    @TypeConverter
    public static String stepsToString(ArrayList<Step> steps) {
        return new Gson().toJson(steps);
    }
}
