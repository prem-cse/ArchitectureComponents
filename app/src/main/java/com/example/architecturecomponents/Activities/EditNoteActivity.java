package com.example.architecturecomponents.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.architecturecomponents.Notes.Note;
import com.example.architecturecomponents.R;
import com.example.architecturecomponents.ViewModels.EditNoteViewModel;

public class EditNoteActivity extends AppCompatActivity {

    private EditText updatedNote;
    public EditNoteViewModel notemodel;
    private Button update;
    private Button cancel;
    private Bundle bundle;
    private String noteID;
    private LiveData<Note> note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        updatedNote = findViewById(R.id.EditeditText);
        update = findViewById(R.id.EditUpdate);
        cancel = findViewById(R.id.EditCancel);

        bundle = getIntent().getExtras();
        if(bundle != null){
            noteID = bundle.getString("note_id");
        }
        notemodel = ViewModelProviders.of(this).get(EditNoteViewModel.class);

        note = notemodel.getNote(noteID);
        note.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                updatedNote.setText(note.getNote());
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newNote = updatedNote.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("note_id",noteID);
                resultIntent.putExtra("note_updated",newNote);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

}
