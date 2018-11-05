package com.r4hu7.recipedoughnut.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

import java.util.ArrayList;

public class WidgetRecipeItem extends BaseAdapter {
    private ArrayList<Recipe> recipes;

    @Override
    public int getCount() {
        if (recipes == null)
            return 0;
        else return recipes.size();
    }

    @Override
    public Object getItem(int i) {
        return recipes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return recipes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(view.getContext()).inflate(R.layout.adapter_widget_recipe_item, viewGroup, false);
        ((TextView) view.findViewById(R.id.tvRecipeName)).setText(((Recipe) getItem(i)).getName());
        return view;
    }
}
