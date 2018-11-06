package com.r4hu7.recipedoughnut.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.data.remote.response.model.Step;
import com.r4hu7.recipedoughnut.databinding.FragmentRecipeStepsBinding;
import com.r4hu7.recipedoughnut.ui.adapter.StepAdapter;
import com.r4hu7.recipedoughnut.ui.vm.RecipeStepsViewModel;
import com.r4hu7.recipedoughnut.util.StepsNavigator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment extends Fragment {
    SnapHelper snapHelper;

    private RecipeStepsViewModel mViewModel;
    @BindView(R.id.rvStepsContainer)
    RecyclerView rvStepsContainer;
    private FragmentRecipeStepsBinding binding;
    private StepAdapter adapter;
    private WeakReference<StepsNavigator> stepsNavigator;

    public static RecipeStepsFragment newInstance() {
        return new RecipeStepsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecipeStepsViewModel.class);
        if (getActivity() instanceof StepsNavigator)
            stepsNavigator = new WeakReference<>((StepsNavigator) getActivity());
        else
            throw new ClassCastException("Parent activity must implement StepsNavigator");
        initData();
        initRecyclerView();
    }


    private void initData() {
        if (adapter == null) {
            adapter = new StepAdapter(
                    stepsNavigator.get().getRecipe().get() != null ?
                            stepsNavigator.get().getRecipe().get().getSteps() :
                            new ArrayList<>(), mViewModel);
        }
        stepsNavigator.get().getRecipe().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ObservableField<Recipe> recipe = (ObservableField<Recipe>) sender;
                adapter.setSteps(recipe.get().getSteps());
            }
        });
        stepsNavigator.get().getSelectedStep().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ObservableInt stepIndex = (ObservableInt) sender;
                rvStepsContainer.smoothScrollToPosition(stepIndex.get());
                binding.setSelectedStep(stepIndex);
                binding.setRecipeDescription(stepsNavigator.get().getRecipe().get().getSteps().get(stepIndex.get()).getShortDescription());
            }
        });
    }

    private int getSnapPosition(SnapHelper snapHelper, RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        return layoutManager.getPosition(snapHelper.findSnapView(layoutManager));
    }

    private void initRecyclerView() {
        if (rvStepsContainer.getAdapter() == null) {
            rvStepsContainer.setAdapter(adapter);
//            if (stepsNavigator.get().getSelectedStep().get() > 0)
//                rvStepsContainer.scrollToPosition(stepsNavigator.get().getSelectedStep().get());
        }
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvStepsContainer);
        rvStepsContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Step step = stepsNavigator.get().getRecipe().get().getSteps().get(getSnapPosition(snapHelper, recyclerView));
                binding.setRecipeDescription(step.getShortDescription());
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.releasePlayer((StepAdapter.ViewHolder) rvStepsContainer.findViewHolderForAdapterPosition(getSnapPosition(snapHelper, rvStepsContainer)));
    }
}
