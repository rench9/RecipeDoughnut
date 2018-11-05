package com.r4hu7.recipedoughnut.ui.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.RecipeRepository;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.di.component.DaggerRepositoryComponent;
import com.r4hu7.recipedoughnut.di.module.ContextModule;
import com.r4hu7.recipedoughnut.ui.fragment.RecipeFragment;
import com.r4hu7.recipedoughnut.ui.fragment.RecipeListFragment;
import com.r4hu7.recipedoughnut.ui.fragment.RecipeStepsFragment;
import com.r4hu7.recipedoughnut.ui.vm.RecipeViewModel;
import com.r4hu7.recipedoughnut.util.StepsNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements StepsNavigator {
    public final static String RECIPE_KEY = "ARG_RECIPE_KEY";
    private static final String RECIPE_FRAG = "RECIPE_FRAG";
    private static final String RECIPE_STEPS_FRAG = "RECIPE_STEPS_FRAG";
    @BindView(R.id.rootView)
    ViewGroup rootView;
    @BindView(R.id.tbPrimary)
    Toolbar tbPrimary;
    private RecipeFragment recipeFragment;
    private RecipeListFragment recipeListFragment;
    private int recipeId;
    private RecipeRepository repository;
    private RecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        setSupportActionBar(tbPrimary);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();

        if (rootView.getTag().equals("tablet")) {
        } else {
            findOrCreateRecipeFragment();
        }

    }

    private void initData() {
        if (getIntent().hasExtra(RECIPE_KEY)) {
            recipeId = getIntent().getExtras().getInt(RECIPE_KEY);
        }

        repository = DaggerRepositoryComponent.builder().contextModule(new ContextModule(getApplicationContext())).build().getRecipeRepository();

        viewModel = ViewModelProviders.of(this, new ViewModelProvider.NewInstanceFactory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RecipeViewModel(recipeId, repository);
            }
        }).get(RecipeViewModel.class);
        viewModel.getRecipe().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ObservableField<Recipe> recipe = (ObservableField<Recipe>) sender;
                RecipeActivity.this.getSupportActionBar().setTitle(recipe.get().getName());
            }
        });
    }

    private RecipeFragment findOrCreateRecipeFragment() {
        RecipeFragment fragment = (RecipeFragment) getSupportFragmentManager().findFragmentByTag(RECIPE_FRAG);
        if (fragment == null) {
            fragment = RecipeFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.flContainer, fragment, RECIPE_FRAG).commit();
        }
        return fragment;
    }

    private RecipeStepsFragment findOrCreateRecipeStepsFragment() {
        RecipeStepsFragment fragment = (RecipeStepsFragment) getSupportFragmentManager().findFragmentByTag(RECIPE_STEPS_FRAG);
        if (fragment == null) {
            fragment = RecipeStepsFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.flContainer, fragment, RECIPE_STEPS_FRAG).addToBackStack(RECIPE_STEPS_FRAG).commit();
        }
        return fragment;
    }

    @Override
    public void showStep(int stepIndex) {
        viewModel.getSelectedStep().set(stepIndex);
        if (rootView.getTag().equals("phone")) {
            findOrCreateRecipeStepsFragment();
        }
    }

    @Override
    public ObservableField<Recipe> getRecipe() {
        return viewModel.getRecipe();
    }

    @Override
    public ObservableInt getSelectedStep() {
        return viewModel.getSelectedStep();
    }
}
