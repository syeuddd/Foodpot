package com.example.ehteshs1.foodpot;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by syedehteshamuddin on 2018-03-24.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityRecyclerViewTest {

    private static final int RECIPE_POSITION = 1;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void scrollToItemInTheRecipeList_checkItsText(){

        onView(ViewMatchers.withId(R.id.recipeListrecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(1));

        onView(withText("Brownies")).check(matches(isDisplayed()));
    }


}
