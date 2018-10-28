package com.r4hu7.recipedoughnut.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

@Database(entities = {Recipe.class},
        version = 1,
        exportSchema = false)
public abstract class RecipeDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "_main";
    private static RecipeDataBase INSTANCE;

    public abstract RecipeDao recipeDao();

    public static RecipeDataBase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecipeDataBase.class, DATABASE_NAME).build();
        return INSTANCE;
    }
}
