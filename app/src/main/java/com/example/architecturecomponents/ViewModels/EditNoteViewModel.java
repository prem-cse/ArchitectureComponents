package com.example.architecturecomponents.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.architecturecomponents.Notes.Note;
import com.example.architecturecomponents.Notes.NoteDAO;
import com.example.architecturecomponents.Notes.NoteRoomDatabase;

public class EditNoteViewModel extends AndroidViewModel {

    private static final String TAG = "EditNoteViewModel";
    private NoteDAO noteDAO;
    private NoteRoomDatabase database;

    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG,"Edit Viewmodel");
        database = NoteRoomDatabase.getDatabase(application);
        noteDAO = database.noteDAO();

    }

    /*
    wrapper functions
     */
    public LiveData<Note> getNote(String noteID){
        return noteDAO.getNote(noteID);
    }

}
