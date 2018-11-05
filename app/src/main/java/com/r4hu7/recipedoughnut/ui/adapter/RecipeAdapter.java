package com.r4hu7.recipedoughnut.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.RecipeConfig;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.databinding.AdapterRecipeCardBinding;
import com.r4hu7.recipedoughnut.util.RecipeNavigator;

import java.lang.ref.WeakReference;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ObservableList<Recipe> recipes;
    private RecipeNavigator recipeNavigator;

    public RecipeAdapter(ObservableList<Recipe> recipes, RecipeNavigator recipeNavigator) {
        this.recipes = recipes;
        this.recipeNavigator = recipeNavigator;
        this.recipes.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Recipe>>() {
            @Override
            public void onChanged(ObservableList<Recipe> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Recipe> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Recipe> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Recipe> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Recipe> sender, int positionStart, int itemCount) {

            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterRecipeCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_recipe_card, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setPosition(i);
        viewHolder.setRecipe(recipes.get(i));
        if (recipeNavigator != null)
            viewHolder.setNavigator(recipeNavigator);
    }

    @Override
    public int getItemCount() {
        if (recipes == null)
            return 0;
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AdapterRecipeCardBinding binding;

        ViewHolder(@NonNull AdapterRecipeCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setRecipe(Recipe recipe) {
            binding.setRecipe(recipe);
        }

        void setPosition(int itemPosition) {
            binding.setImageOnLeft(itemPosition % 2 == 0);
            binding.setDrawableId(RecipeConfig.PLATE_DRAWABLES[itemPosition % 10]);
        }

        void setNavigator(RecipeNavigator navigator) {
            binding.setNavigator(new WeakReference<>(navigator));
        }
    }
}
