package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

/*
 * This class is the GalleryActivity of the app.
 * It displays a list of people and their images in a RecyclerView.
 */
public class GalleryActivity extends AppCompatActivity {
    // Variable to keep track of the sort order
    private boolean sortAscending = true;
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private List<Person> personList;

    // Define an ActivityResultLauncher for the ACTION_GET_CONTENT intent
    private ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    if (uri != null) {
                        promptForName(uri);
                    }
                }
            });

    /* This method prompts the user to enter a name for the image and adds the image to the list of people.
     * It creates a new Person object with the name and Uri and adds it to the list.
     * It also notifies the adapter that the data has changed.
     */
    private void promptForName(Uri imageUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GalleryActivity.this);
        builder.setTitle("Enter Name for the Image");

        // Set up the input
        final EditText input = new EditText(GalleryActivity.this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String personName = input.getText().toString().trim();

                // Validate the entered name
                if (!personName.isEmpty()) {
                    // Create a new Person object with the name and Uri
                    Person newPerson = new Person(personName, imageUri);

                    // Add the new Person to the list
                    personList.add(newPerson);

                    // Notify the adapter that the data has changed
                    adapter.notifyItemInserted(personList.size() - 1);
                } else {
                    // Show a toast message and re-prompt the dialog
                    Toast.makeText(GalleryActivity.this, "Name cannot be empty. Please enter a name.", Toast.LENGTH_LONG).show();
                    promptForName(imageUri); // Re-prompt for name
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                // Handle the cancel case if necessary
            }
        });

        builder.show();
    }

    /* This method shows a dialog to confirm the removal of an image from the list.
     * It removes the item from the list and notifies the adapter of the item removal.
     */
    public void showRemovalDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Remove Image")
                .setMessage("Are you sure you want to remove this image?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the item from the list
                        personList.remove(position);
                        // Notify the adapter of the item removal
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, personList.size());
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    /*
        * This method sorts the list of people alphabetically in ascending or descending order.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void sortListAlphabetically(boolean ascending) {
        if (ascending) {
            // Sort in alphabetical order
            Collections.sort(personList, new Comparator<Person>() {
                @Override
                public int compare(Person p1, Person p2) {
                    return p1.getName().compareTo(p2.getName());
                }
            });
        } else {
            // Sort in reverse alphabetical order
            Collections.sort(personList, new Comparator<Person>() {
                @Override
                public int compare(Person p1, Person p2) {
                    return p2.getName().compareTo(p1.getName());
                }
            });
        }

        // Notify the adapter of the change in the dataset
        adapter.notifyDataSetChanged();
    }


    /*
        * This method is called when the activity is created.
        * It initializes the RecyclerView and the adapter with the list of people.
        * It also sets the onClickListener for the "add image" button.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Initializing the RecyclerView
        recyclerView = findViewById(R.id.recyclerView_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Accessing the shared list from the Application class
        QuizApplication quizApp = (QuizApplication) getApplicationContext();
        personList = quizApp.getPersonList();

        // Initializing the adapter with the list of Person objects
        adapter = new PersonAdapter(personList, this);
        recyclerView.setAdapter(adapter);

        // Initialize the "add image" button and set its onClickListener
        Button buttonAddImage = findViewById(R.id.button_add);
        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the ACTION_GET_CONTENT intent to select an image
                mGetContent.launch("image/*");
            }
        });

        Button buttonSort = findViewById(R.id.button_sort);
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sort the list with the current order
                sortListAlphabetically(sortAscending);

                // Toggle the sort order for the next click
                sortAscending = !sortAscending;

                // Update the button text according to the sort order
                buttonSort.setText(sortAscending ? "Sort Z-A" : "Sort A-Z");
            }
        });

    }
}
