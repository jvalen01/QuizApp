package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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

    private QuizViewModel quizViewModel;

    private ImageView personImageView;
    private Button buttonOption1, buttonOption2, buttonOption3;
    private TextView scoreTextView;
    private List<Person> personList = new ArrayList<>();
    private Person currentPerson;

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

        // Initialize QuizViewModel
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        // Observe the LiveData from the ViewModel
        observeViewModel();

        // Fetch and setup the first question
        quizViewModel.fetchPersons(); // Assuming you have this method to fetch and set the initial list in ViewModel
    }

    private void observeViewModel() {
        quizViewModel.getPersonList().observe(this, persons -> {
            personList = persons;
            setupNextQuestion();
        });

        quizViewModel.getScore().observe(this, score -> updateScoreDisplay());
        quizViewModel.getAttempts().observe(this, attempts -> updateScoreDisplay());
    }

    private void updateScoreDisplay() {
        Integer score = quizViewModel.getScore().getValue();
        Integer attempts = quizViewModel.getAttempts().getValue();
        scoreTextView.setText("Score: " + (score != null ? score : 0) + "/" + (attempts != null ? attempts : 0));
    }

    private void setupNextQuestion() {
        if (personList.isEmpty()) {
            Toast.makeText(this, "No persons available", Toast.LENGTH_SHORT).show();
            return;
        }

        Collections.shuffle(personList);
        currentPerson = personList.get(0);

        // Assuming you have a method to update the image view
        updatePersonImage(currentPerson);

        List<Button> buttons = new ArrayList<>(Arrays.asList(buttonOption1, buttonOption2, buttonOption3));
        Collections.shuffle(buttons);

        Button correctButton = buttons.remove(0);
        correctButton.setText(currentPerson.getName());
        correctButton.setOnClickListener(view -> handleAnswer(true));

        for (Button wrongButton : buttons) {
            wrongButton.setText(getRandomWrongAnswer(currentPerson.getName()));
            wrongButton.setOnClickListener(view -> handleAnswer(false));
        }
    }

    private void handleAnswer(boolean isCorrect) {
        quizViewModel.updateScore(isCorrect);
        if (isCorrect) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect. The correct answer is " + currentPerson.getName(), Toast.LENGTH_LONG).show();
        }
        setupNextQuestion();
    }

    private String getRandomWrongAnswer(String correctAnswer) {
        String wrongAnswer;
        do {
            wrongAnswer = personList.get((int) (Math.random() * personList.size())).getName();
        } while (wrongAnswer.equals(correctAnswer));
        return wrongAnswer;
    }
    private void updatePersonImage(Person currentPerson) {
        if (currentPerson.hasImageResId()) {
            personImageView.setImageResource(currentPerson.getImageResId());
        } else if (currentPerson.hasImageUri()) {
            // Load image from URI using Android's ContentResolver and ImageView's setImageURI
            personImageView.setImageURI(currentPerson.getImageUri());
            // Note: For large images or for better performance and caching, consider using an image loading library like Glide or Picasso.
        } else {
            // Fallback image in case there's no image available
            personImageView.setImageResource(R.drawable.default_image); // Replace with your actual default image resource
        }
    }
}
