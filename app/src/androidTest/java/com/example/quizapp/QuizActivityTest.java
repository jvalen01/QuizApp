package com.example.quizapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;

import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {

    @Rule
    public ActivityScenarioRule<QuizActivity> quizActivityRule =
            new ActivityScenarioRule<>(QuizActivity.class);

    @Test
    public void testScoreUpdateAfterAnswer() {
        // The activity is launched automatically by the ActivityScenarioRule.

        // Simulate clicking the correct answer
        onView(withTagValue(is((Object) "correct"))).perform(click());
        // Check if the score is "1/1"
        onView(withId(R.id.score_text_view)).check(matches(withText("Score: 1/1")));

        // Simulate clicking the wrong answer
        onView(withTagValue(is((Object) "wrong1"))).perform(click());
        // Check if the score is "1/2"
        onView(withId(R.id.score_text_view)).check(matches(withText("Score: 1/2")));
    }
}

