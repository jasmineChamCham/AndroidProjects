package com.example.contactapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAllContacts();

    @Insert
    void insert(Contact ...contacts);

    @Delete
    void delete(Contact contact);

    @Update
    void update(Contact contact);
}
