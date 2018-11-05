package com.r4hu7.recipedoughnut.ui.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.RecipeRepository;
import com.r4hu7.recipedoughnut.databinding.FragmentRecipeListBinding;
import com.r4hu7.recipedoughnut.di.component.DaggerRepositoryComponent;
import com.r4hu7.recipedoughnut.di.module.ContextModule;
import com.r4hu7.recipedoughnut.ui.activity.RecipeActivity;
import com.r4hu7.recipedoughnut.ui.adapter.RecipeAdapter;
import com.r4hu7.recipedoughnut.ui.vm.RecipeListViewModel;
import com.r4hu7.recipedoughnut.util.RecipeNavigator;
import com.r4hu7.recipedoughnut.util.RecyclerViewItemDecorator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements RecipeNavigator {


    private RecipeListViewModel mViewModel;
    private RecipeAdapter adapter;
    private RecyclerViewItemDecorator itemDecorator;

    private RecipeRepository repository;
    @BindView(R.id.rvContainer)
    RecyclerView rvContainer;

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentRecipeListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (repository == null)
            repository = DaggerRepositoryComponent.builder().contextModule(new ContextModule(getContext())).build().getRecipeRepository();

        mViewModel = ViewModelProviders.of(this, new ViewModelProvider.NewInstanceFactory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RecipeListViewModel(repository);
            }
        }).get(RecipeListViewModel.class);
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (adapter == null)
            adapter = new RecipeAdapter(mViewModel.getRecipes(), RecipeListFragment.this);
        if (itemDecorator == null) {
            if (rvContainer.getTag().equals(getResources().getString(R.string.tablet)))
                itemDecorator = new RecyclerViewItemDecorator(true, OrientationHelper.VERTICAL, true, 3, 68);
            else if (rvContainer.getTag().equals(getResources().getString(R.string.phone_land)))
                itemDecorator = new RecyclerViewItemDecorator(true, OrientationHelper.VERTICAL, true, 3, 48);
            else
                itemDecorator = new RecyclerViewItemDecorator(false, OrientationHelper.VERTICAL, false, 48);
        }
        rvContainer.setAdapter(adapter);
        rvContainer.addItemDecoration(itemDecorator);
    }

    @Override
    public void showRecipe(int recipeId) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        bundle.putInt(RecipeActivity.RECIPE_KEY, recipeId);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
