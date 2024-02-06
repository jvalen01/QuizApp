package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

/*
 * This class is the main activity of the app.
 * It displays the main screen with two buttons to navigate to the GalleryActivity and the QuizActivity.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the buttons
        Button buttonGallery = findViewById(R.id.button_gallery);
        Button buttonQuiz = findViewById(R.id.button_quiz);

        // Set the onClick listener for the gallery button
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the GalleryActivity
                Intent galleryIntent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(galleryIntent);
            }
        });

        // Set the onClick listener for the quiz button
        buttonQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the QuizActivity
                Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(quizIntent);
            }
        });
    }

}