package com.r4hu7.recipedoughnut.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.LoadItemCallback;
import com.r4hu7.recipedoughnut.data.remote.response.model.Ingredient;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.di.component.DaggerRepositoryComponent;
import com.r4hu7.recipedoughnut.di.module.ContextModule;

import java.util.List;

import static com.r4hu7.recipedoughnut.ui.widget.RecipesWidget.RECIPE_ID;
import static com.r4hu7.recipedoughnut.ui.widget.RecipesWidget.RECIPE_NAME;
import static com.r4hu7.recipedoughnut.ui.widget.RecipesWidget.SHOW_INGREDIENT;

public class RecipeWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory, LoadItemCallback<List<Recipe>> {
    private Context context;
    private Intent intent;
    private List<Recipe> recipes;
    private int selectedRecipeId;
    private boolean showIngredients;


    RecipeWidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        this.selectedRecipeId = intent.getIntExtra(RECIPE_ID, -1);
        this.showIngredients = intent.getBooleanExtra(SHOW_INGREDIENT, false);
    }

    private void loadData() {
        final long identityToken = Binder.clearCallingIdentity();
        DaggerRepositoryComponent.builder().contextModule(new ContextModule(context)).build().getRecipeRepository().getRecipesMaybe(this);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.adapter_widget_recipe_item);

        if (this.showIngredients) {
            Ingredient ingredient = recipes.get(selectedRecipeId).getIngredients().get(position);
            remoteViews.setTextViewText(R.id.tvRecipeName,
                    String.format("• %s", ingredient.getIngredient()));
            remoteViews.setTextViewText(R.id.tvQuantity,
                    String.format("%s %s", ingredient.getQuantity(), ingredient.getMeasure().toLowerCase()));
            remoteViews.setViewVisibility(R.id.tvQuantity, View.VISIBLE);

        } else {
            remoteViews.setTextViewText(R.id.tvRecipeName, recipes.get(position).getName());
            remoteViews.setViewVisibility(R.id.tvQuantity, View.GONE);
      /*          Intent fillInIntent = new Intent();
                remoteViews.setOnClickFillInIntent(remoteViews.getLayoutId(), fillInIntent);*/
            Bundle extras = new Bundle();
            extras.putInt(RECIPE_ID, position);
            extras.putString(RECIPE_NAME, recipes.get(position).getName());
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            // Make it possible to distinguish the individual on-click
            // action of a given item
            remoteViews.setOnClickFillInIntent(R.id.llRoot, fillInIntent);

        }
        return remoteViews;
    }

    @Override
    public void onCreate() {
        loadData();
    }

    @Override
    public void onDataSetChanged() {
//        loadData();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (recipes == null)
            return 0;

        if (this.showIngredients)
            return recipes.get(selectedRecipeId).getIngredients().size();
        return recipes.size();
    }

    @Override
    public long getItemId(int i) {
        if (recipes == null || showIngredients)
            return i;
        return recipes.get(i).getId();
    }

    @Override
    public RemoteViews getLoadingView() {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.adapter_widget_recipe_item);
        remoteViews.setTextViewText(R.id.tvRecipeName, "Loading...");
        return remoteViews;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onItemLoaded(List<Recipe> recipes) {
        this.recipes = recipes;
        RecipesWidget.sendRefreshBroadcast(context);
//        RecipesWidget.showIngredientBroadcast(context, 2, recipes.get(2).getName());
    }

    @Override
    public void onDataNotAvailable(Throwable e) {

    }
}
