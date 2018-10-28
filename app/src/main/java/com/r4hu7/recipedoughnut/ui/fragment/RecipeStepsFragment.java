package com.r4hu7.recipedoughnut.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.ui.vm.RecipeStepsViewModel;

public class RecipeStepsFragment extends Fragment {

    private RecipeStepsViewModel mViewModel;

    public static RecipeStepsFragment newInstance() {
        return new RecipeStepsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_steps, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecipeStepsViewModel.class);
        // TODO: Use the ViewModel
    }

}
