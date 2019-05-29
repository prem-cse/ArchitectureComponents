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

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private static final String TAG = "NoteViewModel";
    private NoteDAO noteDAO;
    private NoteRoomDatabase database;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        database = NoteRoomDatabase.getDatabase(application);
        noteDAO = database.noteDAO();
        allNotes = noteDAO.getAllNotes();
    }

    /*
        WRAPPER FUNCTION FOR DAO
        insert data into view model
     */
    public void insert(Note note){
        new InsertAsyncTask(noteDAO).execute(note);
    }
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
    public void update(Note note){
        new UpdateAsyncTask(noteDAO).execute(note);
    }

    public void delete(Note note){
        new DeleteAsyncTask(noteDAO).execute(note);
    }

    private class UpdateAsyncTask extends AsyncTask<Note,Void,Void> {
        NoteDAO noteDAO;
        public UpdateAsyncTask(NoteDAO noteDAO) {

            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Note,Void,Void> {
        NoteDAO noteDAO;
        public DeleteAsyncTask(NoteDAO noteDAO) {

            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.delete(notes[0]);
            return null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG,"android view model is destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Note,Void,Void>{

        NoteDAO mNoteDAO;

        public InsertAsyncTask(NoteDAO mNoteDAO) {
            this.mNoteDAO = mNoteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDAO.insert(notes[0]);
            return null;
        }
    }
}
