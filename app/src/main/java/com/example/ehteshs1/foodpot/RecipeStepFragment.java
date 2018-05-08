package com.example.ehteshs1.foodpot;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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


public class RecipeStepFragment extends Fragment {


    private ArrayList<Step> currentStepList;
    private int counter = 0;

    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private ExtractorsFactory extractorsFactory;
    private TrackSelection.Factory trackSelectionFactory;
    private TrackSelector trackSelector;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private DataSource.Factory dataSourceFactory;
    private MediaSource mediaSource;
    private Context mContext;
    private boolean tabletLayout;
    private boolean loadedFromSavedInstanceState;
    private boolean videoPlayStatus = true;

     @BindView(R.id.playerView) SimpleExoPlayerView recipeView;
     @BindView(R.id.noVideoErrorTextView) TextView errorTextView;
     @BindView(R.id.stepDescription) TextView recipeDescription;
     @BindView(R.id.nextStepButton) Button nextButton;
     @BindView(R.id.previousStepButton) Button previousButton;
     @BindView(R.id.thumbnailView) ImageView thumbnailImageView;

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

        initPlayer();

        if (savedInstanceState!=null){
            videoPlayStatus = savedInstanceState.getBoolean("playStatus");
            long currentPosition = savedInstanceState.getLong("currentPosition");
            counter = savedInstanceState.getInt("currentItemPosition");
            player.seekTo(currentPosition);
        }

        player.setPlayWhenReady(videoPlayStatus);


        if (currentStepList != null) {
            if (counter>=0){

            }

            Step currentStep = currentStepList.get(counter);
            String url="";
            String  thumburl="";
            if (currentStep.getVideoURL().isEmpty()){

                if (!currentStep.getThumbnailURL().isEmpty()){

                     thumburl = currentStep.getThumbnailURL();
                }
            }else {
                url = currentStep.getVideoURL();
            }
            recipeDescription.setText(currentStep.getDescription());


           changeMedia(url,thumburl);
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
                        if (counter < currentStepList.size()) {

                            if (counter+1==currentStepList.size()){
                                nextButton.setVisibility(View.INVISIBLE);
                            }

                            if (previousButton.getVisibility() == View.INVISIBLE) {
                                previousButton.setVisibility(View.VISIBLE);
                            }
                            recipeDescription.setText(currentStepList.get(counter).getDescription());
                            player.setPlayWhenReady(false);

                            changeMedia(currentStepList.get(counter).getVideoURL(),currentStepList.get(counter).getThumbnailURL());

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

                        if (counter < currentStepList.size()) {
                            recipeDescription.setText(currentStepList.get(counter).getDescription());
                            player.setPlayWhenReady(false);

                            changeMedia(currentStepList.get(counter).getVideoURL(),currentStepList.get(counter).getThumbnailURL());


                            if (nextButton.getVisibility() == View.INVISIBLE) {
                                nextButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            });
        }

        return rootView;


    }

    private void initPlayer(){

        bandwidthMeter = new DefaultBandwidthMeter();

        extractorsFactory = new DefaultExtractorsFactory();

        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(trackSelectionFactory);

        defaultBandwidthMeter = new DefaultBandwidthMeter();

        dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "foodPot"), defaultBandwidthMeter);

        player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putBoolean("playStatus",player.getPlayWhenReady());
        outState.putLong("currentPosition",player.getCurrentPosition());
        outState.putInt("currentItemPosition",counter);
        super.onSaveInstanceState(outState);
    }


    private void changeMedia(String url, String mthumbnailUrl) {

            if (!url.isEmpty() && player != null && recipeView != null) {

                mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);

                player.prepare(mediaSource);

                player.setPlayWhenReady(videoPlayStatus);

                recipeView.requestFocus();
                recipeView.setPlayer(player);

                recipeView.setVisibility(View.VISIBLE);
                thumbnailImageView.setVisibility(View.GONE);
                errorTextView.setVisibility(View.GONE);

            } else if (!TextUtils.isEmpty(mthumbnailUrl)) {

                boolean fileFormat = mthumbnailUrl.endsWith("mp4");

                if (!fileFormat) {

                    recipeView.setVisibility(View.GONE);
                    errorTextView.setVisibility(View.GONE);
                    thumbnailImageView.setVisibility(View.VISIBLE);

                    Picasso.get()
                            .load(mthumbnailUrl)
                            .into(thumbnailImageView);
                }else {

                    recipeView.setVisibility(View.GONE);
                    errorTextView.setVisibility(View.VISIBLE);
                    thumbnailImageView.setVisibility(View.GONE);
                }

            } else {

                recipeView.setVisibility(View.GONE);
                errorTextView.setVisibility(View.VISIBLE);
                thumbnailImageView.setVisibility(View.GONE);
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

    }

    @Override
    public void onStop() {
        super.onStop();
       releasePlayer();
    }


}
