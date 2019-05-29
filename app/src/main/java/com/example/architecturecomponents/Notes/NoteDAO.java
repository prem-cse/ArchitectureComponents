package com.example.architecturecomponents.Notes;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    /*
    list of operations
    definition will be in view model class
     */
    @Insert
    void insert(Note note);

    @Query("SELECT * FROM NOTES")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM NOTES WHERE ID =:noteID")
    LiveData<Note> getNote(String noteID);

    @Update
    void update(Note note);

    @Delete
    int delete(Note note);

}
