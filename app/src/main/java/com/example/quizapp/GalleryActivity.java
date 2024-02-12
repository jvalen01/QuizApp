package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Comparator;
import java.util.List;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;

/*
 * This class is the GalleryActivity of the app.
 * It displays a list of people and their images in a RecyclerView.
 */
public class GalleryActivity extends AppCompatActivity {
    // Variable to keep track of the sort order
    private boolean sortAscending = true;
    private PersonAdapter adapter;
    private List<Person> personList;

    // ActivityResultLauncher for the ACTION_GET_CONTENT intent
    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                // Handle the returned Uri
                if (uri != null) {
                    // Take persistable URI permission
                    try {
                        getContentResolver().takePersistableUriPermission(uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                    promptForName(uri);
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
        builder.setPositiveButton("OK", (dialog, which) -> {
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
                //If empty: Show a toast message and re-prompt the dialog
                Toast.makeText(GalleryActivity.this, "Name cannot be empty. Please enter a name.", Toast.LENGTH_LONG).show();
                promptForName(imageUri); // Re-prompt for name
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    /* This method shows a dialog to confirm the removal of an image from the list.
     * It removes the item from the list and notifies the adapter of the item removal.
     */
    public void showRemovalDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Remove Image")
                .setMessage("Are you sure you want to remove this image?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Remove the item from the list
                    personList.remove(position);
                    // Notify the adapter of the item removal
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, personList.size());
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
            personList.sort(Comparator.comparing(Person::getName));
        } else {
            // Sort in reverse alphabetical order
            personList.sort((p1, p2) -> p2.getName().compareTo(p1.getName()));
        }

        // Notify the adapter of the change in the dataset
        adapter.notifyDataSetChanged();
    }


    /*
        * This method is called when the activity is created.
        * It initializes the RecyclerView and the adapter with the list of people.
        * It also sets the onClickListener for the "add image" button and sort button.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Initializing the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Accessing the shared list from the Application class
        QuizApplication quizApp = (QuizApplication) getApplicationContext();
        personList = quizApp.getPersonList();

        adapter = new PersonAdapter(personList, this);
        recyclerView.setAdapter(adapter);

        // Add image functionality:
        Button buttonAddImage = findViewById(R.id.button_add);
        buttonAddImage.setOnClickListener(v -> {
            // Launch the ACTION_GET_CONTENT intent to select an image
            mGetContent.launch("image/*");
        });

        // Sorting functionality:
        Button buttonSort = findViewById(R.id.button_sort);
        buttonSort.setOnClickListener(v -> {
            // Sort the list with the current order
            sortListAlphabetically(sortAscending);

            // Toggle the sort order for the next click
            sortAscending = !sortAscending;

            // Update the button text according to the sort order
            buttonSort.setText(sortAscending ? "Sort A-Z" : "Sort Z-A");
        });

    }
}
