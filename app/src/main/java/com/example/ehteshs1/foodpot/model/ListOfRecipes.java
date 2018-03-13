package com.example.ehteshs1.foodpot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ehteshs1 on 2018/03/13.
 */

public class ListOfRecipes {
    @SerializedName("recipies")
    @Expose
    private List<Recipy> recipies = null;


    public List<Recipy> getRecipies() {
        return recipies;
    }

    public void setRecipies(List<Recipy> recipies) {
        this.recipies = recipies;
    }
}
