package com.r4hu7.recipedoughnut.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.remote.response.model.Ingredient;
import com.r4hu7.recipedoughnut.databinding.AdapterRecipeIngredientBinding;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;

    public IngredientsAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterRecipeIngredientBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_recipe_ingredient, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setIngredient(ingredients.get(i));
    }

    @Override
    public int getItemCount() {
        if (ingredients == null)
            return 0;
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AdapterRecipeIngredientBinding binding;

        ViewHolder(@NonNull AdapterRecipeIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setIngredient(Ingredient ingredient) {
            binding.setIngredient(ingredient);
        }

    }
}
