package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private List<Person> personList;
    private Person currentPerson;
    private TextView scoreTextView;
    private ImageView personImageView;
    private Button buttonOption1, buttonOption2, buttonOption3;
    private int score = 0;
    private int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize the views
        scoreTextView = findViewById(R.id.score_text_view);
        personImageView = findViewById(R.id.person_image_view);
        buttonOption1 = findViewById(R.id.button_option_1);
        buttonOption2 = findViewById(R.id.button_option_2);
        buttonOption3 = findViewById(R.id.button_option_3);

        // Get the Person list from the Application class
        QuizApplication quizApp = (QuizApplication) getApplicationContext();
        personList = new ArrayList<>(quizApp.getPersonList());

        setupNextQuestion();
    }

    private void setupNextQuestion() {

        // Shuffle the list and pick the first Person as the current question
        Collections.shuffle(personList);
        currentPerson = personList.get(0);

        // Check if currentPerson has an image URI or a resource ID, and load the image accordingly
        if (currentPerson.hasImageUri()) {
            personImageView.setImageURI(currentPerson.getImageUri());
        } else if (currentPerson.hasImageResId()) {
            personImageView.setImageResource(currentPerson.getImageResId());
        } else {
            // Handle the case where there's no image
            personImageView.setImageResource(R.drawable.default_image); // Replace with your default image
        }





        // Create a list of indices excluding the index of the correct answer
        List<Integer> indices = new ArrayList<>();
        for (int i = 1; i < personList.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        // Set the correct name on a random button
        List<Button> buttons = new ArrayList<>(Arrays.asList(buttonOption1, buttonOption2, buttonOption3));
        Collections.shuffle(buttons);

        Button correctButton = buttons.remove(0); // Remove the button to avoid adding the name again
        correctButton.setText(currentPerson.getName());
        correctButton.setOnClickListener(view -> handleAnswer(true));

        // Set incorrect names on the other buttons
        for (Button wrongButton : buttons) {
            Person wrongPerson = personList.get(indices.remove(0)); // Removing the index to avoid duplicate names
            wrongButton.setText(wrongPerson.getName());
            wrongButton.setOnClickListener(view -> handleAnswer(false));
        }
    }

    private void handleAnswer(boolean isCorrect) {
        if (isCorrect) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect. The correct answer is " + currentPerson.getName(), Toast.LENGTH_LONG).show();
        }
        attempts++;
        updateScore();
        setupNextQuestion();
    }

    private void updateScore() {
        scoreTextView.setText("Score: " + score + "/" + attempts);
    }
}

