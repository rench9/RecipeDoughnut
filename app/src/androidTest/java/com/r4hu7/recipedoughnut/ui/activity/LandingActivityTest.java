package com.r4hu7.recipedoughnut.ui.activity;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.r4hu7.recipedoughnut.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LandingActivityTest {

    @Rule
    public ActivityTestRule<LandingActivity> recipeTestRule = new ActivityTestRule<>(LandingActivity.class, true, true);
    private IdlingResource mIdlingResource;

    @Before
    public void setUp() {
        mIdlingResource = recipeTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
        recipeTestRule.getActivity().getSupportFragmentManager().beginTransaction().commit();

    }

    @Test
    public void textFragmentLoading() {
        onView(withId(R.id.flContainer)).check(matches((isDisplayed())));

    }

    @After
    public void tearDown() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}