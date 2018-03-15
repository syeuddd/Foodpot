package com.example.ehteshs1.foodpot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.model.Ingredient;
import com.example.ehteshs1.foodpot.model.Recipy;
import com.example.ehteshs1.foodpot.model.Step;

import java.util.ArrayList;

public class IngredientListViewAdapter extends RecyclerView.Adapter<IngredientListViewAdapter.MyViewHolder>{

    private ArrayList<Ingredient> ingredientList;
    private String recipeStepDetails="";
    private boolean isTextViewClicked = false;

    private Context mContext;


    public IngredientListViewAdapter(Context context){
        mContext = context;
    }

    public void setData(ArrayList<Ingredient> selectedRecipeIngredientList){
        ingredientList = selectedRecipeIngredientList;
        notifyDataSetChanged();
    }


    @Override
    public IngredientListViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mainView = LayoutInflater.from(mContext).inflate(R.layout.ingredient_list_item, parent,false);

        return new MyViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(final IngredientListViewAdapter.MyViewHolder holder, final int position) {

        if (ingredientList !=null){

            Ingredient ingredient = ingredientList.get(position);

            if (ingredient!=null){

                String ingName = ingredient.getIngredient();

                if (ingName!=null && !ingName.isEmpty()){
                    holder.ingredientName.setText(ingredient.getIngredient());
                }else {
                    holder.ingredientName.setText("Name not available");
                }

                String ingQty = ingredient.getQuantity();
                if (ingQty!=null && !ingQty.isEmpty()){
                    holder.ingredientQuantity.setText(ingredient.getQuantity());
                }else {
                    holder.ingredientQuantity.setText("Qty not available");
                }

                String ingMeasure = ingredient.getMeasure();
                if (ingMeasure !=null && !ingMeasure.isEmpty() ){
                    holder.ingredientMeasure.setText(ingredient.getMeasure());
                }else {
                    holder.ingredientMeasure.setText("Measure not available");
                }

            }

        }

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext,IngredientListViewAdapter.this.recipeStepDetails,Toast.LENGTH_SHORT).show();

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

        TextView ingredientName;
        TextView ingredientQuantity;
        TextView ingredientMeasure;

        public MyViewHolder(View itemView) {
            super(itemView);

            ingredientName = itemView.findViewById(R.id.ingredientName);
            ingredientQuantity = itemView.findViewById(R.id.ingredientQuantity);
            ingredientMeasure = itemView.findViewById(R.id.ingredientMeasure);
        }

    }
}
