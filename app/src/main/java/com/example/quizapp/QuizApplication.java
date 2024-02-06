package com.example.quizapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class QuizApplication extends Application {

    private List<Person> personList;

    @Override
    public void onCreate() {
        super.onCreate();
        initializePersonList();
    }

    private void initializePersonList() {
        personList = new ArrayList<>();
        personList.add(new Person("Erna Solberg", R.drawable.erna));
        personList.add(new Person("Leonel Messi", R.drawable.messi));
        personList.add(new Person("Christiano Ronaldo", R.drawable.ronaldo));

    }

    public List<Person> getPersonList() {
        return personList;
    }

}
