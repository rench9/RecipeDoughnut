package com.r4hu7.recipedoughnut.ui.vm;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private ObservableList<Recipe> recipes = new ObservableArrayList<>();

    public void setRecipes(List<Recipe> recipes) {
        this.recipes.addAll(recipes);
    }

    public ObservableList<Recipe> getRecipes() {
        return recipes;
    }
}
