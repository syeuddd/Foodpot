package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ehteshs1.foodpot.model.Recipy;

import java.util.ArrayList;

public class DetailRecipeActivity extends AppCompatActivity {


    DetailRecipeViewAdapter adapter;
    Recipy mRecipy;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        Intent intent = getIntent();
        mRecipy = intent.getParcelableExtra("recipeDetails");



        if (findViewById(R.id.twoPaneLayout)!=null){
            mTwoPane = true;

            //setting up stuff for detail recipe view

        }




        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable("recipe",mRecipy);

        DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
        detailRecipeFragment.setArguments(recipeBundle);


        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipeDetailFragment,detailRecipeFragment)
                .commit();


        setTitle(mRecipy.getName());

    }
}
