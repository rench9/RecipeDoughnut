package com.r4hu7.recipedoughnut.ui.vm;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.widget.ImageView;

import com.r4hu7.recipedoughnut.data.LoadItemCallback;
import com.r4hu7.recipedoughnut.data.RecipeRepository;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.di.module.GlideApp;

public class RecipeViewModel extends ViewModel {
    RecipeRepository repository;
    private int recipeId;
    private ObservableField<Recipe> recipe = new ObservableField<>();
    private ObservableInt selectedStep = new ObservableInt();

    public RecipeViewModel(int recipeId, RecipeRepository recipeRepository) {
        this.recipeId = recipeId;
        this.repository = recipeRepository;
        this.selectedStep.set(-1);
        loadRecipe();
    }

    @BindingAdapter("setThumbnailImage")
    public static void setThumbnailImage(ImageView view, String url) {
        if (url != null && !url.isEmpty())
            GlideApp.with(view.getContext())
                    .asBitmap()
                    .fitCenter()
                    .load(url)
                    .into(view);
    }

    @BindingAdapter("setThumbnail")
    public static void setThumbnail(ImageView view, int drawableId) {
        if (drawableId != -1)
            GlideApp.with(view.getContext())
                    .asBitmap()
                    .fitCenter()
                    .load(drawableId)
                    .into(view);
    }

    public void loadRecipe() {
        repository.getRecipe(recipeId, new LoadItemCallback<Recipe>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onItemLoaded(Recipe recipe) {
                RecipeViewModel.this.recipe.set(recipe);
            }

            @Override
            public void onDataNotAvailable(Throwable e) {

            }
        });
    }

    public void setRecipe(Recipe recipe) {
        this.recipe.set(recipe);
    }

    public ObservableField<Recipe> getRecipe() {
        return recipe;
    }

    public ObservableInt getSelectedStep() {
        return selectedStep;
    }

    public void setSelectedStep(ObservableInt selectedStep) {
        this.selectedStep = selectedStep;
    }
}
