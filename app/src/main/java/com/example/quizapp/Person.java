package com.example.quizapp;

import android.net.Uri;

public class Person {
    private String name;
    private int imageResId = -1; // Resource ID for the drawable
    private Uri imageUri; // URI for the image

    // Constructor for resource ID
    public Person(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    // Constructor for URI
    public Person(String name, Uri imageUri) {
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public boolean hasImageUri() {
        return imageUri != null;
    }

    public boolean hasImageResId() {
        return imageResId != -1;
    }
}
