package com.example.ehteshs1.foodpot;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.Utils.NetworkUtils;
import com.example.ehteshs1.foodpot.controller.FetchRecipes;
import com.example.ehteshs1.foodpot.model.Recipy;
import com.example.ehteshs1.foodpot.network.ApiService;
import com.example.ehteshs1.foodpot.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

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


       // LinearLayoutManager manager = new LinearLayoutManager(this);

        StaggeredGridLayoutManager manager;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        }else {
            manager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        }

        recipleTitleRecyclerView.setLayoutManager(manager);

        recipleTitleRecyclerView.setAdapter(adapter);

        String baseUrl = "https://d17h27t6h515a5.cloudfront.net";

        recipies = new ArrayList<>();

        ProgressBar bar = findViewById(R.id.progressBar);

        RetrofitClient retrofitClient = new RetrofitClient(this);

        if (isDevicedConnected() && NetworkUtils.isConnected()){

            ApiService service = retrofitClient.getRetrofitClient(baseUrl).create(ApiService.class);

            retrofit2.Call<List<Recipy>> call = service.getRecipes();

            FetchRecipes task = new FetchRecipes(this);

            call.enqueue(task);
        }else {
            Toast.makeText(this,"Device not connected to internet",Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    public void fetchCompleted(Response<List<Recipy>> response) {

        recipies = (ArrayList<Recipy>) response.body();

        adapter.setData(recipies);

    }

    private Boolean isDevicedConnected(){

        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
