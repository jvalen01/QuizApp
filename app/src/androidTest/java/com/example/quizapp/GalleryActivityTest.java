package com.example.quizapp;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {

    // Test data
    private List<Person> createTestData() {
        List<Person> testData = new ArrayList<>();
        testData.add(new Person("Charlie", null));
        testData.add(new Person("Alice", null));
        testData.add(new Person("Bob", null));
        return testData;
    }

    @Test
    public void sortingTest() {
        // Context of the app under test
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Create test data
        List<Person> testData = createTestData();

        // Sort the list in alphabetical order
        Collections.sort(testData, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });

        // Check if the list is sorted alphabetically
        assertEquals("Alice", testData.get(0).getName());
        assertEquals("Bob", testData.get(1).getName());
        assertEquals("Charlie", testData.get(2).getName());

        // Sort the list in reverse alphabetical order
        Collections.sort(testData, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p2.getName().compareTo(p1.getName());
            }
        });

        // Check if the list is sorted in reverse alphabetical order
        assertEquals("Charlie", testData.get(0).getName());
        assertEquals("Bob", testData.get(1).getName());
        assertEquals("Alice", testData.get(2).getName());
    }
}
