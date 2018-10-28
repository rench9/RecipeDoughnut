package com.r4hu7.recipedoughnut.di.component;


import com.r4hu7.recipedoughnut.data.RecipeRepository;
import com.r4hu7.recipedoughnut.di.module.RepositoryModule;

import dagger.Component;

@Component(modules = {RepositoryModule.class})
public interface RepositoryComponent {
    RecipeRepository getRecipeRepository();
}
