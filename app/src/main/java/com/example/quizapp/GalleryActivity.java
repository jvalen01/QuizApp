package com.example.quizapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ActivityResultLauncher<Intent> takePhotoLauncher;

    private ImageView imageImageView;
    private AlertDialog entryDialog;

    private EditText nameEditText; // Now a member variable

    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private List<Person> personList;
    private Button addEntryButton;

    private static final int PERMISSIONS_REQUEST_READ_STORAGE = 1;
    private static final int PERMISSIONS_REQUEST_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerView_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addEntryButton = findViewById(R.id.button_add);

        personList = new ArrayList<>();
        // Example data, replace with actual image resource names
        personList.add(new Person("Christiano Ronaldo", R.drawable.ronaldo));
        personList.add(new Person("Ollie Watkins", R.drawable.watkins));
        personList.add(new Person("John McGinn", R.drawable.mcginn));
        // Add more Person objects to the list

        adapter = new PersonAdapter(personList);
        recyclerView.setAdapter(adapter);





    }

}
