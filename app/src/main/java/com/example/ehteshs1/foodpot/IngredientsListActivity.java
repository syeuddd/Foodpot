package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.model.Ingredient;
import com.example.ehteshs1.foodpot.model.Recipy;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListActivity extends AppCompatActivity {

    private IngredientListViewAdapter adapter;
    private ArrayList<Ingredient> ingredientArrayList;
     @BindView(R.id.ingredientRecyclerView) RecyclerView ingredientRecyclerView;
    private SharedPreferences sharedPrefs;
    private Gson gson;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        ButterKnife.bind(this);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        gson = new Gson();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        ingredientRecyclerView = findViewById(R.id.ingredientRecyclerView);

        adapter = new IngredientListViewAdapter(this);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        ingredientRecyclerView.setLayoutManager(manager);

        ingredientRecyclerView.setAdapter(adapter);

        Intent intent = getIntent();

            recipeName = intent.getStringExtra("recipeName");
            if (recipeName != null){
                if (!recipeName.isEmpty())
                    setTitle(recipeName);
            }

            boolean loadedFromWidget = intent.getBooleanExtra("loadedFromwidget",false);

            if (loadedFromWidget){
                if(loadIngredientsFromLocalStorage()){
                    adapter.setData(ingredientArrayList);
                }
            }
            ingredientArrayList = intent.getParcelableArrayListExtra("ingredientList");
            if (ingredientArrayList!=null){
                adapter.setData(ingredientArrayList);
            }

    }


    private Boolean loadIngredientsFromLocalStorage(){

        String jSon = sharedPrefs.getString("ingredientInfo", "");

        if (!jSon.equals("")){

            Recipy storedIngredientList = gson.fromJson(jSon, Recipy.class);

            ingredientArrayList = (ArrayList<Ingredient>) storedIngredientList.getIngredients();
            recipeName = storedIngredientList.getName();

            setTitle(recipeName);

            if (ingredientArrayList!=null){
                if(ingredientArrayList.size()>0){
                    return true;
                }

            }

        }

       // Toast.makeText(this,"App has been launched from widget",Toast.LENGTH_SHORT).show();
        return false;
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
