package com.example.ehteshs1.foodpot;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.ehteshs1.foodpot.model.Ingredient;
import com.example.ehteshs1.foodpot.model.Recipy;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Implementation of App Widget functionality.
 */
public class IgredientsListWidget extends AppWidgetProvider {

    private static IngredientListViewAdapter adapter;
    private static ArrayList<Ingredient> ingredientArrayList;
    @BindView(R.id.ingredientRecyclerView)
    RecyclerView ingredientRecyclerView;
    private static SharedPreferences sharedPrefs;
    private static Gson gson;
    private String recipeName;
    private static Context mContext;
    private static StringBuilder builder;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        loadIngredientsFromLocalStorage();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.igredients_list);

        if (ingredientArrayList!=null){
            builder = new StringBuilder(ingredientArrayList.size());
            for (int i =0; i<ingredientArrayList.size(); i++){

                Ingredient recipe = ingredientArrayList.get(i);
                String ingredient = recipe.getIngredient();

                String completeList = builder.append(ingredient+" ,").toString();

                Log.i("widgetIngredientsLoaded",completeList);
                views.setTextViewText(R.id.appwidget_text, completeList);
            }
        }
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        mContext = context;

        // Construct the RemoteViews object




        Intent widgetActivity = new Intent(context,IngredientsListActivity.class);
        widgetActivity.putExtra("loadedFromwidget",true);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,widgetActivity,PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);





        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        mContext = context;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    private static void loadIngredientsFromLocalStorage(){

        gson = new Gson();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        String jSon = sharedPrefs.getString("ingredientInfo", "");

        if (!jSon.equals("")){

            Recipy storedIngredientList = gson.fromJson(jSon, Recipy.class);

            ingredientArrayList = (ArrayList<Ingredient>) storedIngredientList.getIngredients();
           // recipeName = storedIngredientList.getName();

            //setTitle(recipeName);


        }
    }
}

