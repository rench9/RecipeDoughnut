package com.r4hu7.recipedoughnut.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.LoadItemCallback;
import com.r4hu7.recipedoughnut.data.RecipeRepository;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.di.component.DaggerRepositoryComponent;
import com.r4hu7.recipedoughnut.di.module.ContextModule;

public class RecipeActivity extends AppCompatActivity {
    public final static String RECIPE_KEY = "ARG_RECIPE_KEY";

    private int recipeId = -1;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        initDate();
    }

    private void initDate() {
        if (getIntent().hasExtra(RECIPE_KEY)) {
            recipeId = getIntent().getExtras().getInt(RECIPE_KEY);
            RecipeRepository repository = DaggerRepositoryComponent.builder().contextModule(new ContextModule(getApplicationContext())).build().getRecipeRepository();
            repository.getRecipe(recipeId, new LoadItemCallback<Recipe>() {
                @Override
                public void onLoading() {

                }

                @Override
                public void onItemLoaded(Recipe recipe) {
                    RecipeActivity.this.recipe = recipe;
                }

                @Override
                public void onDataNotAvailable(Throwable e) {

                }
            });
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
