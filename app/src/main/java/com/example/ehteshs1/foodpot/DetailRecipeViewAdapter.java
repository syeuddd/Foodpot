package com.example.ehteshs1.foodpot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.model.Ingredient;
import com.example.ehteshs1.foodpot.model.Recipy;
import com.example.ehteshs1.foodpot.model.Step;

import java.util.ArrayList;

public class DetailRecipeViewAdapter extends RecyclerView.Adapter<DetailRecipeViewAdapter.MyViewHolder>{

    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Step> recipeSteps;
    private Recipy mSelectedRecipe;
    private String recipeStepDetails="";
    private boolean isTextViewClicked = false;

    private Context mContext;


    public DetailRecipeViewAdapter(Context context){
        mContext = context;
    }

    public void setData(Recipy selectedRecipe){
        mSelectedRecipe = selectedRecipe;
        ingredientList = (ArrayList<Ingredient>) selectedRecipe.getIngredients();
        recipeSteps = (ArrayList<Step>) selectedRecipe.getSteps();
        notifyDataSetChanged();
    }


    @Override
    public DetailRecipeViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mainView = LayoutInflater.from(mContext).inflate(R.layout.recipe_detail_view_item_layout, parent,false);

        return new MyViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(final DetailRecipeViewAdapter.MyViewHolder holder, final int position) {



        if (position==0){

            holder.recipeDetails.setText("Ingredients");

        }else {

            Step recipeStep = recipeSteps.get(position-1);
            recipeStepDetails = recipeStep.getDescription();

            if (recipeStepDetails!=null){
                holder.recipeDetails.setText(recipeStepDetails);
            }else {
                holder.recipeDetails.setText("No value entered");
            }

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext,DetailRecipeViewAdapter.this.recipeStepDetails,Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        if (ingredientList ==null){
            return 0;
        }
        return recipeSteps.size()+1;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView recipeDetails;

        public MyViewHolder(View itemView) {
            super(itemView);

            recipeDetails = itemView.findViewById(R.id.recipe);
        }

    }
}
