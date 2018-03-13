package com.example.ehteshs1.foodpot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.model.Recipy;

import java.util.ArrayList;

/**
 * Created by ehteshs1 on 2018/03/13.
 */

public class MainViewRecipeAdapter extends RecyclerView.Adapter<MainViewRecipeAdapter.MyViewHolder>{

    private ArrayList<Recipy> recipyArrayList;
    private Context mContext;


    public MainViewRecipeAdapter(Context context){
        mContext = context;
    }

    public void setData(ArrayList<Recipy> recipies){
        recipyArrayList = recipies;
        notifyDataSetChanged();
    }


    @Override
    public MainViewRecipeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mainView = LayoutInflater.from(mContext).inflate(R.layout.recipe_main_layout,parent,false);

        return new MyViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(MainViewRecipeAdapter.MyViewHolder holder, int position) {

        Recipy currentRecipe = recipyArrayList.get(position);

        final String title = currentRecipe.getName();

        holder.recipeTitle.setText(title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,title,Toast.LENGTH_SHORT).show();
//                Intent recipeDetailIntent = new Intent(mContext,DetailRecipeActivity.class);
//                mContext.startActivity(recipeDetailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (recipyArrayList==null){
            return 0;
        }
        return recipyArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView recipeTitle;

        public MyViewHolder(View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipeTitle);
        }

    }
}
