package com.example.quizapp;

public class Person {
    private String name;
    private int imageResId; // Resource ID for the drawable

    public Person(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    // If you want to set the name or imageResId after the object has been created, add the setters below
    public void setName(String name) {
        this.name = name;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getImageResource() {
        return imageResId;
    }
}


