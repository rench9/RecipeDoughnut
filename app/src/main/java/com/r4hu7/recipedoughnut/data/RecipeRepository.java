package com.r4hu7.recipedoughnut.data;

import android.support.annotation.NonNull;

import com.r4hu7.recipedoughnut.data.local.LocalDataSource;
import com.r4hu7.recipedoughnut.data.remote.RemoteDataSource;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

import java.util.List;

import io.reactivex.Maybe;

public class RecipeRepository {
    private static RecipeRepository INSTANCE;
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    private RecipeRepository(@NonNull LocalDataSource localDataSource, @NonNull RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static RecipeRepository getInstance(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        if (INSTANCE == null)
            INSTANCE = new RecipeRepository(localDataSource, remoteDataSource);
        return INSTANCE;
    }

    public Maybe<List<Recipe>> getRecipes() {
        return remoteDataSource.getRecipes();
    }

    public void getRecipe(int recipeId, LoadItemCallback<Recipe> loadItemCallback) {
        localDataSource.getRecipe(recipeId, loadItemCallback);
    }

    public void saveRecipes(Recipe... recipes) {
        localDataSource.saveRecipes(recipes);
    }

}
