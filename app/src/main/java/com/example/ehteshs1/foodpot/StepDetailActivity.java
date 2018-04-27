package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ehteshs1.foodpot.model.Step;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    private ArrayList<Step> currentStepList;
    private int counter = 0;
    TextView description;
    SimpleExoPlayer player;
    BandwidthMeter bandwidthMeter;
    ExtractorsFactory extractorsFactory;
    TrackSelection.Factory trackSelectionFactory;
    TrackSelector trackSelector;
    DefaultBandwidthMeter defaultBandwidthMeter;
    DataSource.Factory dataSourceFactory;
    MediaSource mediaSource;
    String videoUrl;
    SimpleExoPlayerView recipeView;
    TextView errorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent stepDetailsIntent = getIntent();

        currentStepList = stepDetailsIntent.getParcelableArrayListExtra("stepDetails");
        counter = stepDetailsIntent.getIntExtra("currentPosition",0);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("stepList",currentStepList);
        bundle.putInt("counter",counter);

        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.stepDetailFragment,recipeStepFragment)
                .commit();

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


