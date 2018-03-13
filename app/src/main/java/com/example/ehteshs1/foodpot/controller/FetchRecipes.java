package com.example.ehteshs1.foodpot.controller;


import com.example.ehteshs1.foodpot.model.Recipy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ehteshs1 on 2018/03/13.
 */

public class FetchRecipes implements Callback<List<Recipy>>{

    private FetchRecipeInterface mDelegate = null;

    public interface FetchRecipeInterface{
        void fetchCompleted(Response<List<Recipy>> response);
    }

    public FetchRecipes(FetchRecipeInterface delegate){
        mDelegate = delegate;
    }

    @Override
    public void onResponse(Call<List<Recipy>> call, Response<List<Recipy>> response) {

        mDelegate.fetchCompleted(response);
    }

    @Override
    public void onFailure(Call<List<Recipy>> call, Throwable t) {
        mDelegate.fetchCompleted(null);
    }
}
