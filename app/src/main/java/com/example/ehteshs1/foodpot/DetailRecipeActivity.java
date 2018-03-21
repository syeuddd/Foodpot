package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ehteshs1.foodpot.DetailRecipeViewAdapter.OnStepClickListener;
import com.example.ehteshs1.foodpot.model.Ingredient;
import com.example.ehteshs1.foodpot.model.Recipy;
import com.example.ehteshs1.foodpot.model.Step;

import java.util.ArrayList;

public class DetailRecipeActivity extends AppCompatActivity implements OnStepClickListener{


    DetailRecipeViewAdapter adapter;
    Recipy mRecipy;
    private boolean mTwoPane;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mRecipeSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        Intent intent = getIntent();
        mRecipy = intent.getParcelableExtra("recipeDetails");
        mIngredients = (ArrayList<Ingredient>) mRecipy.getIngredients();
        mRecipeSteps = (ArrayList<Step>) mRecipy.getSteps();

        if (findViewById(R.id.twoPaneLayout)!=null){
            mTwoPane = true;
            initializeStepFragment(0);
        }

        initializeRecipeDetailsFragment();

        setTitle(mRecipy.getName());

    }



    @Override
    public void stepSelected(int position) {

        if (!mTwoPane){
            if (position == 0) {
                Intent ingredientIntent = new Intent(this, IngredientsListActivity.class);
                ingredientIntent.putParcelableArrayListExtra("ingredientList", mIngredients);
                startActivity(ingredientIntent);

            } else {
                Intent detailStepIntent = new Intent(this, StepDetailActivity.class);
                detailStepIntent.putParcelableArrayListExtra("stepDetails", mRecipeSteps);
                detailStepIntent.putExtra("currentPosition", position-1);
                startActivity(detailStepIntent);
            }
        }else {

            initializeStepFragment(position);

        }


    }

    private void initializeStepFragment(int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("stepList",mRecipeSteps);
        bundle.putInt("counter",position);

        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.stepDetailFragment,recipeStepFragment)
                .commit();
    }

    private void initializeRecipeDetailsFragment() {
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable("recipe",mRecipy);

        DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
        detailRecipeFragment.setArguments(recipeBundle);


        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipeDetailFragment,detailRecipeFragment)
                .commit();
    }
}
