package com.r4hu7.recipedoughnut.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.recipedoughnut.R;
import com.r4hu7.recipedoughnut.databinding.FragmentRecipeBinding;
import com.r4hu7.recipedoughnut.ui.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {
    @BindView(R.id.tlPrimary)
    TabLayout tlPrimary;
    @BindView(R.id.vpContainer)
    ViewPager vpContainer;
    private FragmentRecipeBinding binding;

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewPager();
    }

    private void initViewPager() {
        tlPrimary.setupWithViewPager(vpContainer);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        pagerAdapter.addFragment(RecipeStepsListFragment.newInstance(), "Steps");
        pagerAdapter.addFragment(RecipeIngredientsFragment.newInstance(), "Ingredients");
        vpContainer.setAdapter(pagerAdapter);
    }

}
