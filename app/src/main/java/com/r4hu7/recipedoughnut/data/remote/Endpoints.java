package com.r4hu7.recipedoughnut.data.remote;

import com.r4hu7.recipedoughnut.data.remote.response.model.Recipe;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;

public interface Endpoints {
    @GET("/android-baking-app-json")
    Maybe<List<Recipe>> getRecipes();
}
