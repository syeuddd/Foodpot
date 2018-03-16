package com.example.ehteshs1.foodpot;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.model.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

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
    PlaybackControlView playbackControlView;
    ProgressBar bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent stepDetailsIntent = getIntent();

        currentStepList = stepDetailsIntent.getParcelableArrayListExtra("stepDetails");
        counter = stepDetailsIntent.getIntExtra("currentPosition",0);

        description = findViewById(R.id.stepDescription);
        final Button nextButton = findViewById(R.id.nextStepButton);
        final Button previousButton = findViewById(R.id.previousStepButton);
        bar = findViewById(R.id.progressBar);

        ImageButton playButton = findViewById(R.id.exo_play);
        ImageButton pauseButton = findViewById(R.id.exo_pause);

        initializePlayer(currentStepList.get(counter).getVideoURL());

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(true);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(false);

            }
        });


        if (currentStepList != null){
            Step currentStep = currentStepList.get(counter);
            description.setText(currentStep.getDescription());
        }

        if (counter==0){
            previousButton.setVisibility(View.INVISIBLE);
        }

        if ((counter+1)==currentStepList.size()){

            nextButton.setVisibility(View.INVISIBLE);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter >= 0 && counter <= currentStepList.size()) {
                    counter++;
                    if (counter < currentStepList.size()-1) {
                        if (previousButton.getVisibility()==View.INVISIBLE){
                            previousButton.setVisibility(View.VISIBLE);
                        }
                        StepDetailActivity.this.description.setText(currentStepList.get(counter).getDescription());
                    } else {
                        nextButton.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter > 0 && counter <= currentStepList.size()) {
                    counter--;

                    if (counter==0){
                        previousButton.setVisibility(View.INVISIBLE);
                    }

                    if (counter < currentStepList.size()-1) {
                        StepDetailActivity.this.description.setText(currentStepList.get(counter).getDescription());
                        if (nextButton.getVisibility()==View.INVISIBLE){
                            nextButton.setVisibility(View.VISIBLE);
                        }
                    }else {
                        previousButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

    }

    private void initializePlayer(String url) {

        bandwidthMeter = new DefaultBandwidthMeter();

        extractorsFactory = new DefaultExtractorsFactory();

        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(trackSelectionFactory);

        defaultBandwidthMeter = new DefaultBandwidthMeter();

        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,"foodPot"),defaultBandwidthMeter);

        mediaSource = new ExtractorMediaSource(Uri.parse(url),dataSourceFactory,extractorsFactory,null,null);

        player = ExoPlayerFactory.newSimpleInstance(this,trackSelector);

        player.prepare(mediaSource);

        //player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        SimpleExoPlayerView recipeView = findViewById(R.id.playerView);
        recipeView.requestFocus();
        recipeView.setPlayer(player);

    }
}

