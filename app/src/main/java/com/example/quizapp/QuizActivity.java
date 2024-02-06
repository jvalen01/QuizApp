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

    // Declare the variables used in the quiz activity
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

        // Initialize all the views used in the quiz activity
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

    /* This method sets up the next question in the quiz by shuffling the list of people and picking the first person as the current question.
        It also sets the image of the current person and creates a list of indices excluding the index of the correct answer.
        It then sets the correct name on a random button and the incorrect names on the other buttons.
    */
    private void setupNextQuestion() {

        // Shuffle the list and pick the first Person as the current question
        Collections.shuffle(personList);
        currentPerson = personList.get(0);

        // Set the image of the current person
        personImageView.setImageResource(currentPerson.getImageResId());

        // Create a list of indices excluding the index of the correct answer
        List<Integer> indices = new ArrayList<>();
        for (int i = 1; i < personList.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        // Set the correct name on a random button
        List<Button> buttons = new ArrayList<>(Arrays.asList(buttonOption1, buttonOption2, buttonOption3));
        Collections.shuffle(buttons);

        Button correctButton = buttons.remove(0); // Remove the button to avoid adding the same name to multiple buttons
        correctButton.setText(currentPerson.getName());
        correctButton.setOnClickListener(view -> handleAnswer(true));

        // Set incorrect names on the other buttons
        for (Button wrongButton : buttons) {
            Person wrongPerson = personList.get(indices.remove(0)); // Removing the index avoid adding the same name to multiple buttons
            wrongButton.setText(wrongPerson.getName());
            wrongButton.setOnClickListener(view -> handleAnswer(false));
        }
    }

    /*
        This method handles the answer given by the user. If the answer is correct, the score is incremented and a toast message is shown.
        If the answer is incorrect, a toast message is shown with the correct answer.
        The score is updated and the next question is set up.
     */
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

