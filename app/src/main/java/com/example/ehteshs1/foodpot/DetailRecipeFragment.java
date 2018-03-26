package com.example.ehteshs1.foodpot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ehteshs1.foodpot.model.Recipy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ehteshs1 on 2018/03/20.
 */

public class DetailRecipeFragment extends Fragment {


    DetailRecipeViewAdapter adapter;
    Recipy mRecipy;
    Unbinder mUnbinder;
    @BindView(R.id.recipeDetailViewRecyclerView) RecyclerView detailFragmentRecyclerView;

    public DetailRecipeFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_recipe_fragement, container, false);

        mUnbinder = ButterKnife.bind(this,rootView);

        Context mContext = getActivity();

        RecyclerView recipleDetailRecyclerView = detailFragmentRecyclerView;

        adapter = new DetailRecipeViewAdapter(mContext);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);

        recipleDetailRecyclerView.setLayoutManager(manager);

        recipleDetailRecyclerView.setAdapter(adapter);

        Bundle recipeFromActivity = getArguments();
        mRecipy = recipeFromActivity.getParcelable("recipe");

        if (mRecipy != null){
            adapter.setData(mRecipy);
        }
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}

