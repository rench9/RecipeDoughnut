package com.r4hu7.recipedoughnut.ui.vm;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.di.module.GlideApp;

public class RecipeViewModel extends ViewModel {
    ObservableField<Recipe> recipe = new ObservableField<>();

    @BindingAdapter("setThumbnail")
    public static void setThumbnail(ImageView view, int drawableId) {
        if (drawableId != -1)
            GlideApp.with(view.getContext())
                    .asBitmap()
                    .fitCenter()
                    .load(drawableId)
                    .into(view);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe.set(recipe);
    }

    public ObservableField<Recipe> getRecipe() {
        return recipe;
    }
}
