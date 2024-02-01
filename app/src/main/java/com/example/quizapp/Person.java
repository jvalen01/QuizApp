package com.example.quizapp;

import android.net.Uri;

public class Person {
    private String name;
    private int imageResId; // Resource ID for the drawable
    private Uri imageUri; // for images selected from the gallery

    // Constructor for drawable resources
    public Person(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
        this.imageUri = null;
    }

    // Constructor for Uris
    public Person(String name, Uri imageUri) {
        this.name = name;
        this.imageResId = 0; // default or invalid resource ID
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    // If you want to set the name or imageResId after the object has been created, add the setters below
    public void setName(String name) {
        this.name = name;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

}


