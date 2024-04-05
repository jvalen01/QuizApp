package com.example.quizapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static junit.framework.TestCase.assertEquals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {

    @Rule
    public ActivityScenarioRule<GalleryActivity> activityRule = new ActivityScenarioRule<>(GalleryActivity.class);

    @Before
    public void setUp() {
        Intents.init(); // Initialize Espresso-Intents before each test
    }

    @After
    public void tearDown() {
        Intents.release(); // Release Espresso-Intents after each test
    }

    // Default image Uri for the default image
    private final Uri defaultImageUri = Uri.parse("android.resource://com.example.quizapp/" + R.drawable.default_image);

    @Test
    public void testAddingAndDeletingPersonUpdatesCount() {

        //The initial count of items in the RecyclerView
        final int[] initialCount = new int[1];
        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.recyclerView_gallery);
            initialCount[0] = recyclerView.getAdapter().getItemCount();
        });

        // Stub the Intent to return a default image Uri
        intending(anyIntent()).respondWith(new Instrumentation.ActivityResult(
                Activity.RESULT_OK, new Intent().setData(defaultImageUri)
        ));

        // Click the add button
        onView(withId(R.id.button_add)).perform(click());

        // Wait for the Intent to be caught and stubbed response to be returned
        intended(hasAction(Intent.ACTION_GET_CONTENT));

        // Prompt for the person's name
        onView(withClassName(Matchers.equalTo(EditText.class.getName()))).perform(typeText("New Person"), closeSoftKeyboard());

        // Click the "OK" button to confirm
        onView(withText("OK")).perform(click());

        // Observe the count after adding and asserting that it has increased by 1
        final int[] countAfterAdding = new int[1];
        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.recyclerView_gallery);
            countAfterAdding[0] = recyclerView.getAdapter().getItemCount();
            assertEquals("Count after adding should be increased by 1.", initialCount[0] + 1, countAfterAdding[0]);
        });

        // Click on the first item in the RecyclerView and use a long click to trigger the deletion
        onView(withId(R.id.recyclerView_gallery)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        // Interact with the AlertDialog to confirm deletion
        onView(withText("Yes")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        // Assert that the count has decreased by 1 after deleting
        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.recyclerView_gallery);
            int countAfterDeleting = recyclerView.getAdapter().getItemCount();
            assertEquals("Count after deleting should be decreased by 1.", countAfterAdding[0] - 1, countAfterDeleting);
        });
    }
}

