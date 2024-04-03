package com.example.quizapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

public class QuizViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Person>> personList = new MutableLiveData<>();
    private final MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> attempts = new MutableLiveData<>(0);
    private Person currentPerson;
    private AppDatabase db;

    public QuizViewModel(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application.getApplicationContext(),
                AppDatabase.class, "quiz-database").build();
        loadPersons();
    }

    private void loadPersons() {
        new Thread(() -> {
            List<Person> persons = db.personDao().getAll();
            personList.postValue(persons);
        }).start();
    }

    public LiveData<List<Person>> getPersonList() {
        return personList;
    }
    public void fetchPersons() {
        // Use an ExecutorService to run database operations asynchronously
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Person> persons = db.personDao().getAll();
            personList.postValue(persons);
        });
    }

    public void updateScore(boolean isCorrect) {
        if (isCorrect) {
            score.postValue(score.getValue() + 1);
        }
        attempts.postValue(attempts.getValue() + 1);
    }

    public LiveData<Integer> getScore() {
        return score;
    }

    public LiveData<Integer> getAttempts() {
        return attempts;
    }

    public void setCurrentPerson(Person person) {
        this.currentPerson = person;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void addPerson(Person person) {
        new Thread(() -> {
            db.personDao().insert(person);
            loadPersons();
        }).start();
    }

    public void deletePerson(Person person) {
        new Thread(() -> {
            db.personDao().delete(person);
            loadPersons();
        }).start();
    }
}
