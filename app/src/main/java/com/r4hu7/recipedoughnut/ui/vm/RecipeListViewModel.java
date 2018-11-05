package com.r4hu7.recipedoughnut.ui.vm;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.r4hu7.recipedoughnut.data.LoadItemCallback;
import com.r4hu7.recipedoughnut.data.RecipeRepository;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository repository;

    private ObservableList<Recipe> recipes = new ObservableArrayList<>();

    public RecipeListViewModel(RecipeRepository recipeRepository) {
        repository = recipeRepository;
        loadRecipe();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes.addAll(recipes);
    }

    public ObservableList<Recipe> getRecipes() {
        return recipes;
    }

    public void loadRecipe() {
        repository.getRecipes(new LoadItemCallback<List<Recipe>>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onItemLoaded(List<Recipe> recipes) {
                RecipeListViewModel.this.recipes.addAll(recipes);
                repository.saveRecipes(recipes.toArray(new Recipe[0]));
            }

            @Override
            public void onDataNotAvailable(Throwable e) {

            }
        });
    }
}
