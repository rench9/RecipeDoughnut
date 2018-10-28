package com.r4hu7.recipedoughnut.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.data.remote.response.model.Ingredient;

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
//        AdapterIngredientBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_ingredient, viewGroup, false);
        return new ViewHolder(null);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        viewHolder.setIngredient(ingredients.get(i));
    }

    @Override
    public int getItemCount() {
        if (ingredients == null)
            return 0;
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
  /*      private AdapterIngredientBinding binding;

        ViewHolder(@NonNull AdapterIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }*/
        ViewHolder(@NonNull View binding) {
            super(binding);
        }
//
//        void setIngredient(Ingredient ingredient) {
//            binding.setIngredient(ingredient);
//        }

    }
}
