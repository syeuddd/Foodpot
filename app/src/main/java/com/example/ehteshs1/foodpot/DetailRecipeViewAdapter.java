package com.example.ehteshs1.foodpot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.model.Recipy;

import java.util.ArrayList;

public class DetailRecipeViewAdapter extends RecyclerView.Adapter<DetailRecipeViewAdapter.MyViewHolder>{

    private ArrayList<Recipy> ingredientList;

    private Context mContext;


    public DetailRecipeViewAdapter(Context context){
        mContext = context;
    }

    public void setData(ArrayList<Recipy> recipies){
        ingredientList = recipies;
        notifyDataSetChanged();
    }


    @Override
    public DetailRecipeViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mainView = LayoutInflater.from(mContext).inflate(R.layout.recipe_main_item_layout, parent,false);

        return new MyViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(DetailRecipeViewAdapter.MyViewHolder holder, int position) {

        Recipy currentRecipe = ingredientList.get(position);

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
        if (ingredientList ==null){
            return 0;
        }
        return ingredientList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView recipeTitle;

        public MyViewHolder(View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipeTitle);
        }

    }
}
