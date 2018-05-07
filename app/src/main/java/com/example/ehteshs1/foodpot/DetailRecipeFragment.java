package com.example.ehteshs1.foodpot;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    Context mContext;
    Parcelable savedRecyclerLayoutState ;


    @BindView(R.id.recipeDetailViewRecyclerView) RecyclerView detailFragmentRecyclerView;

    public DetailRecipeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_recipe_fragement, container, false);

        mContext = getActivity();

        adapter = new DetailRecipeViewAdapter(mContext);
        manager = new LinearLayoutManager(mContext);


        Bundle recipeFromActivity = getArguments();

        mRecipy = recipeFromActivity.getParcelable("recipe");

        if (mRecipy != null){
            adapter.setData(mRecipy);
        }

        mUnbinder = ButterKnife.bind(this,rootView);

        detailFragmentRecyclerView.setAdapter(adapter);

        detailFragmentRecyclerView.setLayoutManager(manager);

        return rootView;
    }

}

