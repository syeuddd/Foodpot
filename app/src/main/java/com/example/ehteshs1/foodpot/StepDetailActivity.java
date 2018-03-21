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

    ArrayList<Step> currentStepList;
    int counter = 0;
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


//
//        description = findViewById(R.id.stepDescription);
//        final Button nextButton = findViewById(R.id.nextStepButton);
//        final Button previousButton = findViewById(R.id.previousStepButton);
//        errorTextView = findViewById(R.id.noVideoErrorTextView);
//
//        // initialize exo player stuff
//
//        bandwidthMeter = new DefaultBandwidthMeter();
//
//        extractorsFactory = new DefaultExtractorsFactory();
//
//        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//
//        trackSelector = new DefaultTrackSelector(trackSelectionFactory);
//
//        defaultBandwidthMeter = new DefaultBandwidthMeter();
//
//        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,"foodPot"),defaultBandwidthMeter);
//
//        player = ExoPlayerFactory.newSimpleInstance(this,trackSelector);
//
//        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//
//        recipeView = findViewById(R.id.playerView);
//
////        ImageButton playButton = findViewById(R.id.exo_play);
////        ImageButton pauseButton = findViewById(R.id.exo_pause);
//
//
//        if (currentStepList != null){
//            Step currentStep = currentStepList.get(counter);
//            description.setText(currentStep.getDescription());
//            changeVideo(currentStepList.get(counter).getVideoURL());
//        }
//
//
//        if (counter==0){
//            previousButton.setVisibility(View.INVISIBLE);
//        }
//
//        if ((counter+1)==currentStepList.size()){
//
//            nextButton.setVisibility(View.INVISIBLE);
//        }
//
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (counter >= 0 && counter <= currentStepList.size()) {
//                    counter++;
//                    if (counter < currentStepList.size()-1) {
//                        if (previousButton.getVisibility()==View.INVISIBLE){
//                            previousButton.setVisibility(View.VISIBLE);
//                        }
//                        StepDetailActivity.this.description.setText(currentStepList.get(counter).getDescription());
//                        player.setPlayWhenReady(false);
//                        changeVideo(currentStepList.get(counter).getVideoURL());
//
//                    } else {
//                        nextButton.setVisibility(View.INVISIBLE);
//                    }
//
//                }
//            }
//        });
//
//        previousButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (counter > 0 && counter <= currentStepList.size()) {
//                    counter--;
//
//                    if (counter==0){
//                        previousButton.setVisibility(View.INVISIBLE);
//                    }
//
//                    if (counter < currentStepList.size()-1) {
//                        StepDetailActivity.this.description.setText(currentStepList.get(counter).getDescription());
//                        player.setPlayWhenReady(false);
//                        changeVideo(currentStepList.get(counter).getVideoURL());
//                        if (nextButton.getVisibility()==View.INVISIBLE){
//                            nextButton.setVisibility(View.VISIBLE);
//                        }
//                    }else {
//                        previousButton.setVisibility(View.INVISIBLE);
//                    }
//                }
//            }
//        });

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

//    private void changeVideo(String url) {
//
//        if (!url.isEmpty() && player !=null && recipeView !=null){
//
//            mediaSource = new ExtractorMediaSource(Uri.parse(url),dataSourceFactory,extractorsFactory,null,null);
//
//            player.prepare(mediaSource);
//            player.setPlayWhenReady(true);
//
//            recipeView.requestFocus();
//            recipeView.setPlayer(player);
//
//            recipeView.setVisibility(View.VISIBLE);
//            errorTextView.setVisibility(View.GONE);
//
//        }else {
//
//            recipeView.setVisibility(View.GONE);
//            errorTextView.setVisibility(View.VISIBLE);
//        }
//
//    }
//
//
//    private void releasePlayer(){
//        if (player!=null){
//            player.release();
//            player = null;
//            trackSelector = null;
//        }
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        player.setPlayWhenReady(true);
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        player.setPlayWhenReady(false);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        releasePlayer();
//    }



}


