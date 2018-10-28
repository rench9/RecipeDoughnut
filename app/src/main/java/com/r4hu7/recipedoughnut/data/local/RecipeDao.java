package com.r4hu7.recipedoughnut.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe... recipes);

    @Query("SELECT * FROM _recipe")
    List<Recipe> getRecipes();

    @Query("SELECT * FROM _recipe WHERE id =:id")
    Recipe getRecipe(int id);
}
