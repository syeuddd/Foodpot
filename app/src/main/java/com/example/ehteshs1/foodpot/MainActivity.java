package com.example.ehteshs1.foodpot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.ehteshs1.foodpot.controller.FetchRecipes;
import com.example.ehteshs1.foodpot.model.ListOfRecipes;
import com.example.ehteshs1.foodpot.model.Recipy;
import com.example.ehteshs1.foodpot.network.ApiService;
import com.example.ehteshs1.foodpot.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FetchRecipes.FetchRecipeInterface{


    MainViewRecipeAdapter adapter;
    ArrayList<Recipy> recipies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recipleTitleRecyclerView = findViewById(R.id.recyclerView);

        adapter = new MainViewRecipeAdapter(this);

        recipleTitleRecyclerView.setAdapter(adapter);

        String baseUrl = "https://d17h27t6h515a5.cloudfront.net";

        recipies = new ArrayList<>();

        ProgressBar bar = findViewById(R.id.progressBar);

        RetrofitClient retrofitClient = new RetrofitClient(this);

        ApiService service = retrofitClient.getRetrofitClient(baseUrl).create(ApiService.class);

        retrofit2.Call<List<Recipy>> call = service.getRecipes();

        FetchRecipes task = new FetchRecipes(this);

        call.enqueue(task);

        //setup recycler view




    }


    @Override
    public void fetchCompleted(Response<List<Recipy>> response) {

        recipies = (ArrayList<Recipy>) response.body();

        adapter.setData(recipies);

    }
}
