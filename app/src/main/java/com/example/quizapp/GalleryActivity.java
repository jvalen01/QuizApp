package com.example.quizapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private List<Person> personList;

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
        adapter = new PersonAdapter(personList);
        recyclerView.setAdapter(adapter);
    }
}
