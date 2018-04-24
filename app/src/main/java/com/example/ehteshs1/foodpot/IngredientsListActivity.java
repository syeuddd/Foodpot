package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.ehteshs1.foodpot.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListActivity extends AppCompatActivity {

    IngredientListViewAdapter adapter;
    ArrayList<Ingredient> ingredientArrayList;
    @BindView(R.id.ingredientRecyclerView) RecyclerView ingredientRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        ButterKnife.bind(this);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ingredientRecyclerView = findViewById(R.id.ingredientRecyclerView);

        adapter = new IngredientListViewAdapter(this);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        ingredientRecyclerView.setLayoutManager(manager);

        ingredientRecyclerView.setAdapter(adapter);

        Intent intent = getIntent();



        ingredientArrayList = intent.getParcelableArrayListExtra("ingredientList");

        if (ingredientArrayList != null){
            adapter.setData(ingredientArrayList);
        }else {

            loadIngredientsFromLocalStorage();


        }

        setTitle("Ingredient List");
    }


    private void loadIngredientsFromLocalStorage(){

        //load data from shared preference

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mItem = item.getItemId();
        switch (mItem){
            case android.R.id.home:
                finish();
        }
        return true;
    }

}
