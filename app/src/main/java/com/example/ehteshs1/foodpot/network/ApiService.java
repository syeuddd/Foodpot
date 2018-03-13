package com.example.ehteshs1.foodpot.network;

import com.example.ehteshs1.foodpot.model.ListOfRecipes;
import com.example.ehteshs1.foodpot.model.Recipy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ehteshs1 on 2018/03/13.
 */

public interface ApiService {

   @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipy>> getRecipes();

}
