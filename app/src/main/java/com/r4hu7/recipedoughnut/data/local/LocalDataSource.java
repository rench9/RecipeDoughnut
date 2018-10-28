package com.r4hu7.recipedoughnut.data.local;

import android.support.annotation.NonNull;

import com.r4hu7.recipedoughnut.data.LoadItemCallback;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.util.AppExecutors;

import java.util.List;

public class LocalDataSource {
    private AppExecutors appExecutors;
    private static volatile LocalDataSource INSTANCE;
    private RecipeDataBase dataBase;

    private LocalDataSource(@NonNull AppExecutors appExecutors, @NonNull RecipeDataBase recipeDataBase) {
        this.appExecutors = appExecutors;
        this.dataBase = recipeDataBase;
    }

    public static LocalDataSource getInstance(@NonNull AppExecutors appExecutors, RecipeDataBase recipeDataBase) {
        if (INSTANCE == null)
            INSTANCE = new LocalDataSource(appExecutors, recipeDataBase);
        return INSTANCE;
    }

    public void getRecipes(LoadItemCallback<List<Recipe>> callback) {
        Runnable runnable = () -> {
            List<Recipe> recipes = dataBase.recipeDao().getRecipes();
            if (recipes != null && !recipes.isEmpty())
                callback.onItemLoaded(recipes);
            else callback.onDataNotAvailable(new Exception("No data available"));
        };
        execute(runnable);
    }

    public void getRecipe(int recipeId, LoadItemCallback<Recipe> callback) {
        Runnable runnable = () -> {
            Recipe recipes = dataBase.recipeDao().getRecipe(recipeId);
            if (recipes != null)
                callback.onItemLoaded(recipes);
            else callback.onDataNotAvailable(new Exception("No data available"));
        };
        execute(runnable);
    }

    public void saveRecipes(Recipe... recipes) {
        Runnable runnable = () -> dataBase.recipeDao().insert(recipes);
        execute(runnable);
    }

    private void execute(Runnable runnable) {
        appExecutors.diskIO().execute(runnable);
    }

}
