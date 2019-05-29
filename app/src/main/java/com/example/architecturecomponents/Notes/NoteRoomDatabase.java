package com.example.architecturecomponents.Notes;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Note.class,version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDAO noteDAO();
    private static volatile  NoteRoomDatabase noteRoomDatabaseInstance;

    /*
    instance of database will be returned
     */
   public static NoteRoomDatabase getDatabase(final Context context){
        if(noteRoomDatabaseInstance == null){
            synchronized (NoteRoomDatabase.class){
                if(noteRoomDatabaseInstance == null){
                    noteRoomDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_database").build();
                }
            }
        }

        return noteRoomDatabaseInstance;
    }

}
