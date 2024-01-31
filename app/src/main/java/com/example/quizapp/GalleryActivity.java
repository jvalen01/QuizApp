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

        // Creating a list of Person objects
        personList = new ArrayList<>();
        personList.add(new Person("Erna Solberg", R.drawable.erna)); // Replace with actual image resource names
        personList.add(new Person("Leonel Messi", R.drawable.messi)); // Replace with actual image resource names
        personList.add(new Person("Christiano Ronaldo", R.drawable.ronaldo)); // Replace with actual image resource names
        personList.add(new Person("Ollie Watkins", R.drawable.watkins)); // Replace with actual image resource names
        personList.add(new Person("John McGinn", R.drawable.mcginn)); // Replace with actual image resource names
        // Add more Person objects to the list

        // Initializing the adapter with the list of Person objects
        adapter = new PersonAdapter(personList);
        recyclerView.setAdapter(adapter);
    }
}
