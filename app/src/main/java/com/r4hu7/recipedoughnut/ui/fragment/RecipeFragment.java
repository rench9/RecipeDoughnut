package com.r4hu7.recipedoughnut.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.ui.activity.RecipeActivity;
import com.r4hu7.recipedoughnut.ui.adapter.IngredientsAdapter;
import com.r4hu7.recipedoughnut.ui.adapter.StepAdapter;
import com.r4hu7.recipedoughnut.ui.vm.RecipeViewModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;

public class RecipeFragment extends Fragment {

    private RecipeViewModel mViewModel;

    private IngredientsAdapter ingredientsAdapter;
    private StepAdapter stepAdapter;

    @BindView(R.id.rvIngredientsContainer)
    RecyclerView rvIngredientsContainer;
    @BindView(R.id.rvStepsContainer)
    RecyclerView rvStepsContainer;

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        Recipe recipe = ((RecipeActivity) getActivity()).getRecipe();
        mViewModel.setRecipe(recipe);
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (ingredientsAdapter == null)
            ingredientsAdapter = new IngredientsAdapter(new ObservableArrayList<>());
        if (stepAdapter == null)
            stepAdapter = new StepAdapter(new ArrayList<>());
        mViewModel.getRecipe().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ObservableField<Recipe> recipeObservableField = (ObservableField<Recipe>) sender;
                ingredientsAdapter.setIngredients(Objects.requireNonNull(recipeObservableField.get()).getIngredients());
                stepAdapter.setSteps(Objects.requireNonNull(recipeObservableField.get()).getSteps());
            }
        });
        rvIngredientsContainer.setAdapter(ingredientsAdapter);
        rvStepsContainer.setAdapter(ingredientsAdapter);

    }

}
