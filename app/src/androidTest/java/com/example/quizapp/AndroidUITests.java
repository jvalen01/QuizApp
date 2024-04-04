package com.example.quizapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AndroidUITests {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    // Test to check if the navigation to the QuizActivity is working correctly
    @Test
    public void testNavigationToQuizActivity() {

        // Click on the Quiz button
        onView(withId(R.id.button_quiz)).perform(click());

        // Check if the person image view is displayed that is present in the QuizActivity
        onView(withId(R.id.person_image_view)).check(matches(isDisplayed()));

        // Check if the score text view is displayed that is present in the QuizActivity
        onView(withId(R.id.score_text_view)).check(matches(isDisplayed()));

        // check if the text view with the text "Score: 0" is displayed
        onView(withId(R.id.score_text_view)).check(matches(withText("Score: 0/0")));

        }

    }



