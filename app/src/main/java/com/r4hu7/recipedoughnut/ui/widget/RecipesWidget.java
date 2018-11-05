package com.r4hu7.recipedoughnut.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.r4hu7.recipedoughnut.R;

public class RecipesWidget extends AppWidgetProvider {
    public static final String ACTION_SHOW_INGREDIENT = "ACTION_SHOW_INGREDIENT";
    public static final String ACTION_SHOW_RECIPE = "ACTION_SHOW_RECIPE";


    public static String SHOW_INGREDIENT = "SHOW_INGREDIENT";
    public static String RECIPE_ID = "RECIPE_ID";
    public static String RECIPE_NAME = "RECIPE_NAME";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
        views.setTextViewText(R.id.tvSubTitle, "Cook something sweet");

        views.setViewVisibility(R.id.btnBack, View.GONE);

        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.putExtra(SHOW_INGREDIENT, false);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.lvContainer, intent);

        Intent showIngredientIntent = new Intent(context, RecipesWidget.class);
        showIngredientIntent.setAction(ACTION_SHOW_INGREDIENT);
        showIngredientIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        showIngredientIntent.setData(Uri.parse(showIngredientIntent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, showIngredientIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.lvContainer, toastPendingIntent);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvContainer);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateAppWidgetIngredients(Context context, AppWidgetManager appWidgetManager,
                                           int appWidgetId, int recipeId, String recipeName) {
        try {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
            views.setTextViewText(R.id.tvSubTitle, recipeName + " Ingredients");
            views.setViewVisibility(R.id.btnBack, View.VISIBLE);

            Intent intent = new Intent(context, RecipeWidgetService.class);
            intent.putExtra(SHOW_INGREDIENT, true);
            intent.putExtra(RECIPE_ID, recipeId);
            //the extras are ignored, so we need to set data
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.lvContainer, intent);

            Intent showRecipeIntent = new Intent(context, RecipesWidget.class);
            showRecipeIntent.setAction(ACTION_SHOW_RECIPE);
            showRecipeIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            showRecipeIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent goBackPendingIntent = PendingIntent.getBroadcast(context, 0, showRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.btnBack, goBackPendingIntent);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvContainer);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } catch (Exception e) {
            Log.e("1:ERROR", e.getMessage());
        }

    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, RecipesWidget.class));
        context.sendBroadcast(intent);
    }

    public static void showIngredientBroadcast(Context context, int selectedRecipeId, String recipeName) {
        Intent intent = new Intent(ACTION_SHOW_INGREDIENT);
        intent.putExtra(RECIPE_ID, selectedRecipeId);
        intent.putExtra(RECIPE_NAME, recipeName);
        intent.setComponent(new ComponentName(context, RecipesWidget.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, RecipesWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.lvContainer);
        } else if (action.equals(ACTION_SHOW_INGREDIENT)) {

            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            int rId = intent.getIntExtra(RECIPE_ID, -1);
            String rName = intent.getStringExtra(RECIPE_NAME);
            updateAppWidgetIngredients(context, mgr, appWidgetId, rId, rName);
        } else if (action.equals(ACTION_SHOW_RECIPE)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            updateAppWidget(context, mgr, appWidgetId);
        }

        super.onReceive(context, intent);
    }
}

