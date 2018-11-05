package com.r4hu7.recipedoughnut.data.remote;

import android.support.annotation.NonNull;

import com.r4hu7.recipedoughnut.data.LoadItemCallback;
import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    public void getRecipes(LoadItemCallback<List<Recipe>> loadItemCallback) {
        endpoints.getRecipes().observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MaybeObserver<List<Recipe>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Recipe> recipes) {
                loadItemCallback.onItemLoaded(recipes);
            }

            @Override
            public void onError(Throwable e) {
                loadItemCallback.onDataNotAvailable(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

}
