package com.r4hu7.recipedoughnut.data.remote;

import android.support.annotation.NonNull;

import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSource {
    private static RemoteDataSource INSTANCE;
    private Endpoints endpoints;

    private RemoteDataSource(@NonNull Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    public static RemoteDataSource getInstance(Endpoints endpoints) {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSource(endpoints);
        return INSTANCE;
    }

    public Maybe<List<Recipe>> getRecipes() {
        return endpoints.getRecipes().observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
