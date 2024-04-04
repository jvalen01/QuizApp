package com.example.quizapp;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM people")
    List<Person> getAll();

    @Insert
    void insert(Person person);

    @Delete
    void delete(Person person);

    @Query("DELETE FROM people")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM people")
    int getCount();
}


