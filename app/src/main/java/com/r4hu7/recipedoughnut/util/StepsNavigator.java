package com.r4hu7.recipedoughnut.util;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

public interface StepsNavigator {
    void showStep(int stepIndex);

    ObservableField<Recipe> getRecipe();

    ObservableInt getSelectedStep();
}