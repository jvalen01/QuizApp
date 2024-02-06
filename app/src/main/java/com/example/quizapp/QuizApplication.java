package com.example.quizapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/*
   * This class is used to store the list of people that will be used in the quiz and in the gallery.
 */
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
        personList.add(new Person("Lionel Messi", R.drawable.messi));
        personList.add(new Person("Cristiano Ronaldo", R.drawable.ronaldo));

    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void addPerson(Person person) {
        personList.add(person);
    }

}
