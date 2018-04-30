package com.example.ehteshs1.foodpot;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ehteshs1.foodpot.model.Recipy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DetailRecipeFragment extends Fragment {


    private DetailRecipeViewAdapter adapter;
    private Recipy mRecipy;
    Unbinder mUnbinder;
    private Parcelable listState;
    LinearLayoutManager manager;


    @BindView(R.id.recipeDetailViewRecyclerView) RecyclerView detailFragmentRecyclerView;

    public DetailRecipeFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_recipe_fragement, container, false);

        mUnbinder = ButterKnife.bind(this,rootView);

        Context mContext = getActivity();

        adapter = new DetailRecipeViewAdapter(mContext);

        detailFragmentRecyclerView.setAdapter(adapter);

        Bundle recipeFromActivity = getArguments();

        mRecipy = recipeFromActivity.getParcelable("recipe");

        if (mRecipy != null){
            adapter.setData(mRecipy);
        }

        manager = new LinearLayoutManager(mContext);

        detailFragmentRecyclerView.setLayoutManager(manager);

        if (savedInstanceState!=null){
            //Parcelable savedRecycleLayoutState = savedInstanceState.getParcelable("ListState");
            int lastFirstVisiblePosition = savedInstanceState.getInt("firstPosition");
            ((LinearLayoutManager) detailFragmentRecyclerView.getLayoutManager()).scrollToPositionWithOffset(lastFirstVisiblePosition,0);

        }
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

       // listState = recipleDetailRecyclerView.getLayoutManager().onSaveInstanceState();
        int lastfirstVisiblePosition = ((LinearLayoutManager)detailFragmentRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
       // outState.putParcelable("ListState",listState );
        outState.putInt("firstPosition",lastfirstVisiblePosition);

    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (savedInstanceState!=null){
//            Parcelable savedRecycleLayoutState = savedInstanceState.getParcelable("ListState");
//            recipleDetailRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecycleLayoutState);
//
//        }
//    }



}

