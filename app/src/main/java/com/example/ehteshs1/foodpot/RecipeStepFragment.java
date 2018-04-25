package com.example.ehteshs1.foodpot;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehteshs1.foodpot.model.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by syedehteshamuddin on 2018-03-19.
 */

public class RecipeStepFragment extends Fragment {


    ArrayList<Step> currentStepList;
    int counter = 0;

    SimpleExoPlayer player;
    BandwidthMeter bandwidthMeter;
    ExtractorsFactory extractorsFactory;
    TrackSelection.Factory trackSelectionFactory;
    TrackSelector trackSelector;
    DefaultBandwidthMeter defaultBandwidthMeter;
    DataSource.Factory dataSourceFactory;
    MediaSource mediaSource;
    String videoUrl;
    String thumbNailUrl;
    Context mContext;
    private boolean tabletLayout;

    @BindView(R.id.playerView) SimpleExoPlayerView recipeView;
    @BindView(R.id.noVideoErrorTextView) TextView errorTextView;
    @BindView(R.id.stepDescription) TextView recipeDescription;
    @BindView(R.id.nextStepButton) Button nextButton;
    @BindView(R.id.previousStepButton) Button previousButton;
    @BindView(R.id.thumbNailView) ImageView thumbNailImageView;

    public RecipeStepFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_detail_fragment_layout, container, false);

        ButterKnife.bind(this,rootView);

        if (rootView.findViewById(R.id.tabletLayout) != null) {

            tabletLayout = true;

        } else {
            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
        }

        Bundle data = getArguments();

        currentStepList = data.getParcelableArrayList("stepList");
        counter = data.getInt("counter");

        if (getActivity() != null) {
            mContext = getActivity();
        }


        bandwidthMeter = new DefaultBandwidthMeter();

        extractorsFactory = new DefaultExtractorsFactory();

        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(trackSelectionFactory);

        defaultBandwidthMeter = new DefaultBandwidthMeter();

        dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "foodPot"), defaultBandwidthMeter);

        player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);


        thumbNailUrl = "";
        if (currentStepList != null) {
            Step currentStep = currentStepList.get(counter);
            videoUrl = currentStep.getVideoURL();
            recipeDescription.setText(currentStep.getDescription());

            changeVideo(videoUrl, thumbNailUrl);
        }

        if (!tabletLayout) {


        if (counter == 0) {
            previousButton.setVisibility(View.INVISIBLE);
        }

        if ((counter + 1) == currentStepList.size()) {

            nextButton.setVisibility(View.INVISIBLE);
        }


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter >= 0 && counter <= currentStepList.size()) {
                    counter++;
                    if (counter < currentStepList.size() - 1) {
                        if (previousButton.getVisibility() == View.INVISIBLE) {
                            previousButton.setVisibility(View.VISIBLE);
                        }
                        recipeDescription.setText(currentStepList.get(counter).getDescription());
                        player.setPlayWhenReady(false);
                        changeVideo(currentStepList.get(counter).getVideoURL(), currentStepList.get(counter).getThumbnailURL());

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

                    if (counter == 0) {
                        previousButton.setVisibility(View.INVISIBLE);
                    }

                    if (counter < currentStepList.size() - 1) {
                        recipeDescription.setText(currentStepList.get(counter).getDescription());
                        player.setPlayWhenReady(false);
                        changeVideo(currentStepList.get(counter).getVideoURL(), currentStepList.get(counter).getThumbnailURL());
                        if (nextButton.getVisibility() == View.INVISIBLE) {
                            nextButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        previousButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }


        return rootView;


    }


    private void changeVideo(String videoUrl, String thumbNailUrl) {

        if (!videoUrl.isEmpty() && player != null && recipeView != null) {

            mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), dataSourceFactory, extractorsFactory, null, null);

            player.prepare(mediaSource);
            player.setPlayWhenReady(true);

            recipeView.requestFocus();
            recipeView.setPlayer(player);

            recipeView.setVisibility(View.VISIBLE);
            errorTextView.setVisibility(View.GONE);
            thumbNailImageView.setVisibility(View.GONE);

        } else if (!thumbNailUrl.isEmpty()){

                    thumbNailImageView.setVisibility(View.VISIBLE);
                    recipeView.setVisibility(View.GONE);
                    errorTextView.setVisibility(View.GONE);

                    Picasso.get()
                            .load(thumbNailUrl)
                            .centerCrop()
                            .into(thumbNailImageView);
            
        }else {
            recipeView.setVisibility(View.GONE);
            thumbNailImageView.setVisibility(View.GONE);
            errorTextView.setVisibility(View.VISIBLE);
        }

    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
            trackSelector = null;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


}
