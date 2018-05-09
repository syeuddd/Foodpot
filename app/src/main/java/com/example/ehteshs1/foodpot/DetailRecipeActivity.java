package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.DetailRecipeViewAdapter.OnStepClickListener;
import com.example.ehteshs1.foodpot.model.Ingredient;
import com.example.ehteshs1.foodpot.model.Recipy;
import com.example.ehteshs1.foodpot.model.Step;

import java.util.ArrayList;

public class DetailRecipeActivity extends AppCompatActivity implements OnStepClickListener{

    private Recipy mRecipy;
    private boolean mTwoPane;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mRecipeSteps;
    DetailRecipeFragment detailRecipeFragment;
    Parcelable savedRecyclerLayoutState ;
    RecipeStepFragment recipeStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        mRecipy = intent.getParcelableExtra("recipeDetails");

        mIngredients = (ArrayList<Ingredient>) mRecipy.getIngredients();

        mRecipeSteps = (ArrayList<Step>) mRecipy.getSteps();

        if (findViewById(R.id.twoPaneLayout)!=null){
            mTwoPane = true;
            initializeStepFragment(1);
        }

//        if (savedInstanceState!= null){
//            detailRecipeFragment = (DetailRecipeFragment) getSupportFragmentManager().getFragment(savedInstanceState,"fragment");
//        }

        initializeRecipeDetailsFragment();

        setTitle(mRecipy.getName());

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

    @Override
    public void stepSelected(int position) {

        if (!mTwoPane){
            if (position == 0) {
                Intent ingredientIntent = new Intent(this, IngredientsListActivity.class);
                ingredientIntent.putParcelableArrayListExtra("ingredientList", mIngredients);
                ingredientIntent.putExtra("recipeName",mRecipy.getName());
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

        if (position==0){

            Intent intent = new Intent(this,IngredientsListActivity.class);
            intent.putParcelableArrayListExtra("ingredientList",mIngredients);
            startActivity(intent);

        }else {

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("stepList",mRecipeSteps);
            bundle.putInt("counter",position-1);

            recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.stepDetailFragment,recipeStepFragment)
                    .commit();
        }
    }

    private void initializeRecipeDetailsFragment() {

        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable("recipe",mRecipy);

        detailRecipeFragment = new DetailRecipeFragment();

        detailRecipeFragment.setArguments(recipeBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.recipeDetailFragment,detailRecipeFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Bundle",detailRecipeFragment.manager.onSaveInstanceState());

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        savedRecyclerLayoutState = savedInstanceState.getParcelable("Bundle");

    }

    @Override
    protected void onResume() {
        super.onResume();
        detailRecipeFragment.manager.onRestoreInstanceState(savedRecyclerLayoutState);

    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifecycleTesting","Activity onStop is triggered");

//        if (recipeStepFragment!=null){
//            recipeStepFragment.player.release();
//        }

    }
}
