package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ehteshs1.foodpot.model.Recipy;

import java.util.ArrayList;

public class DetailRecipeActivity extends AppCompatActivity {


    DetailRecipeViewAdapter adapter;
    Recipy mRecipy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);


        RecyclerView recipleDetailRecyclerView = findViewById(R.id.recipeDetailViewRecyclerView);

        adapter = new DetailRecipeViewAdapter(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recipleDetailRecyclerView.setLayoutManager(manager);

        recipleDetailRecyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        mRecipy = intent.getParcelableExtra("recipeDetails");

        if (mRecipy != null){
            adapter.setData(mRecipy);
        }

        setTitle(mRecipy.getName());

    }
}
