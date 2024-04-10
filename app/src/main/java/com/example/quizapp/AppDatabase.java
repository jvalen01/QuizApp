package com.example.quizapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;

    public abstract PersonDao personDao();

    public static synchronized void getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "quiz-database")
                    .build();
            initializeData(instance);
        }
    }
    private static void initializeData(AppDatabase database) {
        Executors.newSingleThreadExecutor().execute(() -> {
            PersonDao personDao = database.personDao();
            if (personDao.getCount() == 0) {
                Uri uriErna = Uri.parse("android.resource://" + "com.example.quizapp" + "/" + R.drawable.erna);
                Uri uriMessi = Uri.parse("android.resource://" + "com.example.quizapp" + "/" + R.drawable.messi);
                Uri uriRonaldo = Uri.parse("android.resource://" + "com.example.quizapp" + "/" + R.drawable.ronaldo);

                personDao.insert(new Person("Erna Solberg", uriErna));
                personDao.insert(new Person("Lionel Messi", uriMessi));
                personDao.insert(new Person("Cristiano Ronaldo", uriRonaldo));

                Log.d("AppDatabase", "Data initialization complete.");

            }
            // Add more initial persons as needed
        });
    }
}
