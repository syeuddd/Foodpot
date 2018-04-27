package com.example.ehteshs1.foodpot.network;

import com.example.ehteshs1.foodpot.model.Recipy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

   @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipy>> getRecipes();

}
