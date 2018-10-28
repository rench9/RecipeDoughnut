package com.r4hu7.recipedoughnut.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.RecipeRepository;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.databinding.FragmentRecipeListBinding;
import com.r4hu7.recipedoughnut.di.component.DaggerRepositoryComponent;
import com.r4hu7.recipedoughnut.di.module.ContextModule;
import com.r4hu7.recipedoughnut.ui.activity.RecipeActivity;
import com.r4hu7.recipedoughnut.ui.adapter.RecipeAdapter;
import com.r4hu7.recipedoughnut.ui.vm.RecipeListViewModel;
import com.r4hu7.recipedoughnut.util.RecipeNavigator;
import com.r4hu7.recipedoughnut.util.RecyclerViewItemDecorator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

public class RecipeListFragment extends Fragment implements RecipeNavigator {


    private RecipeListViewModel mViewModel;
    private RecipeAdapter adapter;
    private RecyclerViewItemDecorator itemDecorator;

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
        mViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (adapter == null)
            adapter = new RecipeAdapter(new ObservableArrayList<>(), RecipeListFragment.this);
        if (itemDecorator == null)
            itemDecorator = new RecyclerViewItemDecorator(false, OrientationHelper.VERTICAL, false, 48);
        mViewModel.getRecipes().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Recipe>>() {
            @Override
            public void onChanged(ObservableList<Recipe> sender) {
            }

            @Override
            public void onItemRangeChanged(ObservableList<Recipe> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<Recipe> sender, int positionStart, int itemCount) {
                adapter.setRecipes(sender.subList(0, sender.size()));
            }

            @Override
            public void onItemRangeMoved(ObservableList<Recipe> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Recipe> sender, int positionStart, int itemCount) {

            }
        });
        rvContainer.setAdapter(adapter);
        rvContainer.addItemDecoration(itemDecorator);
        RecipeRepository repository = DaggerRepositoryComponent.builder().contextModule(new ContextModule(getContext())).build().getRecipeRepository();

        repository.getRecipes().subscribe(new MaybeObserver<List<Recipe>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Recipe> recipes) {
                mViewModel.setRecipes(recipes);
                repository.saveRecipes(recipes.toArray(new Recipe[0]));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void showRecipe(int recipeId) {

        Log.e("ID", String.valueOf(recipeId));
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        bundle.putInt(RecipeActivity.RECIPE_KEY, recipeId);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
