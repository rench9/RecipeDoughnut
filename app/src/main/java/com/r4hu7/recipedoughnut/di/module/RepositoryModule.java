package com.r4hu7.recipedoughnut.di.module;

import android.content.Context;

import com.r4hu7.recipedoughnut.data.RecipeRepository;
import com.r4hu7.recipedoughnut.data.local.LocalDataSource;
import com.r4hu7.recipedoughnut.data.local.RecipeDataBase;
import com.r4hu7.recipedoughnut.data.remote.Endpoints;
import com.r4hu7.recipedoughnut.data.remote.RemoteDataSource;
import com.r4hu7.recipedoughnut.util.AppExecutors;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RetrofitModule.class})
public class RepositoryModule {
    @Provides
    public RecipeRepository recipeRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        return RecipeRepository.getInstance(localDataSource, remoteDataSource);
    }

    @Provides
    public RecipeDataBase recipeDataBase(Context context) {
        return RecipeDataBase.getInstance(context);
    }

    @Provides
    public LocalDataSource localDataSource(AppExecutors appExecutors, RecipeDataBase recipeDataBase) {
        return LocalDataSource.getInstance(appExecutors, recipeDataBase);
    }

    @Provides
    public RemoteDataSource remoteDataSource(Endpoints endpoints) {
        return RemoteDataSource.getInstance(endpoints);
    }

    @Provides
    public AppExecutors appExecutors() {
        return new AppExecutors();
    }
}