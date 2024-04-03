package com.example.quizapp;

import android.app.Application;

public class QuizApplication extends Application {
    private static QuizApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.getInstance(this); // Initialize the database
    }

    public static QuizApplication getInstance() {
        return instance;
    }
}
