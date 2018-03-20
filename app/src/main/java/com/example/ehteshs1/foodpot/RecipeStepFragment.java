package com.example.ehteshs1.foodpot;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by syedehteshamuddin on 2018-03-19.
 */

public class RecipeStepFragment extends Fragment{


    public RecipeStepFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_detail_fragment_layout,container,false);

        return rootView;


    }




}
