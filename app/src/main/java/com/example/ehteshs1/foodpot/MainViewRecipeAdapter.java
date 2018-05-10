package com.example.ehteshs1.foodpot;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehteshs1.foodpot.model.Ingredient;
import com.example.ehteshs1.foodpot.model.Recipy;
import com.example.ehteshs1.foodpot.model.Step;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewRecipeAdapter extends RecyclerView.Adapter<MainViewRecipeAdapter.MyViewHolder>{

    private ArrayList<Recipy> recipyArrayList;
    private Context mContext;
    private SharedPreferences sharedPrefs;
    private Gson gson;
    private SharedPreferences.Editor editor;



    public MainViewRecipeAdapter(Context context){

        mContext = context;
        gson =new Gson();


    }

    public void setData(ArrayList<Recipy> recipies){
        recipyArrayList = recipies;
        notifyDataSetChanged();
    }


    @Override
    public MainViewRecipeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mainView = LayoutInflater.from(mContext).inflate(R.layout.recipe_main_item_layout, parent,false);

        return new MyViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(MainViewRecipeAdapter.MyViewHolder holder, int position) {

        final Recipy currentRecipe = recipyArrayList.get(position);

        final String title = currentRecipe.getName();

        holder.recipeTitle.setText(title);

        String recipeImage = currentRecipe.getImage();

        if (!TextUtils.isEmpty(recipeImage)){
            Picasso.get()
                    .load(recipeImage)
                    .into(holder.recipeImage);
        }else {

 //           holder.recipeImage.setImageResource(R.drawable.ic_cake_black);
            Picasso.get()
                    .load(R.drawable.ic_cake_black)
                    .into(holder.recipeImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.i("Picasso-Image-debug","Image loaded successfully");
                        }

                        @Override
                        public void onError(Exception e) {

                           e.printStackTrace();

                        }
                    });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeDetailIntent = new Intent(mContext,DetailRecipeActivity.class);
                recipeDetailIntent.putExtra("recipeDetails",currentRecipe);
                mContext.startActivity(recipeDetailIntent);
                storeIngredientsList(currentRecipe);
            }
        });

    }

    private void storeIngredientsList(Recipy mCurrentRecipy) {

        Recipy storeRecipy = Recipy.getInstance();
        storeRecipy = mCurrentRecipy;
        String IgredientJSon = gson.toJson(storeRecipy);
        sharedPrefs =  PreferenceManager.getDefaultSharedPreferences(mContext);
        //Clear exsiting stuff
        editor = sharedPrefs.edit();
        editor.clear();
        editor.apply();
        // add new stuff
        editor.putString("ingredientInfo", IgredientJSon);

        editor.commit();
        updateWidget();

    }


    private void updateWidget(){


        Intent intent = new Intent(mContext, IgredientsListWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(mContext.getApplicationContext())
                .getAppWidgetIds(new ComponentName(mContext.getApplicationContext(),IgredientsListWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        mContext.sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        if (recipyArrayList==null){
            return 0;
        }
        return recipyArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipeTitle) TextView recipeTitle;
        @BindView(R.id.recipeImage) ImageView recipeImage;



        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }

    }
}
