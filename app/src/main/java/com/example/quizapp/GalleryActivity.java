package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private boolean sortAscending = true;
    private PersonAdapter adapter;
    private QuizViewModel quizViewModel;
    private RecyclerView recyclerView;

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    try {
                        getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                    promptForName(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Initialize QuizViewModel
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        // Observe personList LiveData from QuizViewModel
        quizViewModel.getPersonList().observe(this, this::updateRecyclerView);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup buttons
        setupButtons();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupButtons() {
        // Button to add image
        Button buttonAddImage = findViewById(R.id.button_add);
        buttonAddImage.setOnClickListener(v -> mGetContent.launch("image/*"));

        // Button to sort
        Button buttonSort = findViewById(R.id.button_sort);
        buttonSort.setOnClickListener(v -> {
            sortAscending = !sortAscending;
            List<Person> personList = quizViewModel.getPersonList().getValue();
            if (personList != null) {
                sortListAlphabetically(personList, sortAscending);
                adapter.notifyDataSetChanged();
            }
            buttonSort.setText(sortAscending ? "Sort A-Z" : "Sort Z-A");
        });
    }

    public void showRemovalDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Remove Image")
                .setMessage("Are you sure you want to remove this image?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    List<Person> personList = quizViewModel.getPersonList().getValue();
                    if (personList != null) {
                        personList.remove(position);
                        // Notify the adapter of the item removal
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, personList.size());
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void promptForName(Uri imageUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Name for the Image");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String personName = input.getText().toString().trim();
            if (!personName.isEmpty()) {
                // Create a new Person object with the name and Uri
                Person newPerson = new Person(personName, imageUri);

                // Add the new Person to the list through QuizViewModel
                quizViewModel.addPerson(newPerson);

            } else {
                Toast.makeText(this, "Name cannot be empty. Please enter a name.", Toast.LENGTH_LONG).show();
                promptForName(imageUri); // Re-prompt for name
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void sortListAlphabetically(List<Person> personList, boolean ascending) {
        if (ascending) {
            Collections.sort(personList, Comparator.comparing(Person::getName));
        } else {
            Collections.sort(personList, Comparator.comparing(Person::getName).reversed());
        }
    }

    private void updateRecyclerView(List<Person> personList) {
        Log.d("GalleryActivity", "Updating RecyclerView with person list size: " + personList.size());
        adapter = new PersonAdapter(personList, this);
        recyclerView.setAdapter(adapter);
    }
}
