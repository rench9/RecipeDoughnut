package com.r4hu7.recipedoughnut.ui.fragment;


import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;
import com.r4hu7.recipedoughnut.ui.adapter.StepDescAdapter;
import com.r4hu7.recipedoughnut.util.RecyclerViewItemDecorator;
import com.r4hu7.recipedoughnut.util.StepsNavigator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsListFragment extends Fragment {

    @BindView(R.id.rvContainer)
    RecyclerView rvContainer;
    private StepDescAdapter adapter;
    private RecyclerViewItemDecorator itemDecorator;
    private WeakReference<StepsNavigator> stepsNavigator;

    public RecipeStepsListFragment() {
    }

    public static RecipeStepsListFragment newInstance() {
        RecipeStepsListFragment fragment = new RecipeStepsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof StepsNavigator)
            stepsNavigator = new WeakReference<>((StepsNavigator) getActivity());
        else
            throw new ClassCastException("Parent activity must implement StepsNavigator");
        initData();
        initRecyclerView();
    }

    private void initData() {
        if (adapter == null) {
            adapter = new StepDescAdapter(
                    stepsNavigator.get().getRecipe().get() != null ?
                            stepsNavigator.get().getRecipe().get().getSteps() :
                            new ArrayList<>(),
                    stepsNavigator.get());
        }
        stepsNavigator.get().getRecipe().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ObservableField<Recipe> recipe = (ObservableField<Recipe>) sender;
                adapter.setSteps(recipe.get().getSteps());
            }
        });
    }

    private void initRecyclerView() {
        rvContainer.addItemDecoration(getItemDecorator());
        rvContainer.setAdapter(adapter);
    }

    private DividerItemDecoration getItemDecorator() {

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL) {
            int position;
            int itemCount;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                itemCount = parent.getChildCount();
                position = parent.getChildAdapterPosition(view);
            }

            @Override
            public void setDrawable(@NonNull Drawable drawable) {
                if (position != itemCount - 1)
                    super.setDrawable(drawable);
            }
        };
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.divider_h)));
        return dividerItemDecoration;
    }

}
