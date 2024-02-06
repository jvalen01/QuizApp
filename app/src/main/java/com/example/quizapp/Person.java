package com.example.quizapp;

import android.net.Uri;

/*
 * This class represents a person in the quiz and in the gallery.
 */
public class Person {
    private String name;
    private int imageResId = -1;
    private Uri imageUri;


    public Person(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }


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
