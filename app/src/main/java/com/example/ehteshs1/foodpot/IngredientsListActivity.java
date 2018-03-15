package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ehteshs1.foodpot.model.Ingredient;

import java.util.ArrayList;

public class IngredientsListActivity extends AppCompatActivity {

    IngredientListViewAdapter adapter;
    ArrayList<Ingredient> ingredientArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        RecyclerView ingredientRecyclerView = findViewById(R.id.ingredientRecyclerView);

        adapter = new IngredientListViewAdapter(this);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        ingredientRecyclerView.setLayoutManager(manager);

        ingredientRecyclerView.setAdapter(adapter);

        Intent intent = getIntent();

        ingredientArrayList = intent.getParcelableArrayListExtra("ingredientList");

        if (ingredientArrayList != null){
            adapter.setData(ingredientArrayList);
        }

        setTitle("Ingredient List");
    }
}
