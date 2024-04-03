package com.example.quizapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.net.Uri;

@Entity(tableName = "people")
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "imageResId")
    private int imageResId = -1;

    @ColumnInfo(name = "imageUri")
    private String imageUriString;

    public Person(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public Person(String name, Uri imageUri) {
        this.name = name;
        this.setImageUri(imageUri);
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
    public Uri getImageUri() {
        return imageUriString == null ? null : Uri.parse(imageUriString);
    }

    public void setImageUri(Uri imageUri) {
        this.imageUriString = (imageUri == null) ? null : imageUri.toString();
    }
    // Correct getter for imageUriString
    public String getImageUriString() {
        return imageUriString;
    }

    // Correct setter for imageUriString
    public void setImageUriString(String imageUriString) {
        this.imageUriString = imageUriString;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }


    public boolean hasImageUri() {
        return imageUriString != null;
    }

    public boolean hasImageResId() {
        return imageResId != -1;
    }
}
