package com.example.architecturecomponents.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.architecturecomponents.Adapter;
import com.example.architecturecomponents.MainActivityObserver;
import com.example.architecturecomponents.Notes.Note;
import com.example.architecturecomponents.R;
import com.example.architecturecomponents.ViewModels.DataGenerator;
import com.example.architecturecomponents.ViewModels.NoteViewModel;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements Adapter.OnDeleteClickListener {

    public static final int UPDATE_CODE = 101;
    private static final String TAG = "MainActivity";
    private static final int INSERT_CODE = 100;
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.mainTextview);
        Button button = findViewById(R.id.mainUpdateButton);
        Button newnote = findViewById(R.id.mainNewnote);
        recyclerView = findViewById(R.id.mainRecyclerView);
        adapter = new Adapter(this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Log.i(TAG,"Owner OnCreate");
        /*
        attaching observer
         */
        getLifecycle().addObserver( new MainActivityObserver());
      //  DataGenerator dataGenerator = new DataGenerator();
         final DataGenerator model = ViewModelProviders.of(MainActivity.this).get(DataGenerator.class);
         final LiveData<String> num = model.getNum();

         /*
         update ui on every data change
          */
        num.observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(num.getValue());
            }
        });
         /*
           change data at any time
          */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setNum();
            }
        });

        newnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,NewNoteActivity.class), INSERT_CODE);
            }
        });



        noteViewModel = ViewModelProviders.of(MainActivity.this).get(NoteViewModel.class);
        /*
        ATTACHING OBSERVER TO LIVEDATA
        as we cant reinitialize adapter with updated list of notes
        we make list of noted as live data
        and explicitly set all the notes in adapter
         */
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNote(notes);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == INSERT_CODE && resultCode == RESULT_OK){

            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id,data.getStringExtra("note_added"));
            noteViewModel.insert(note);
            print("Saved");
        }else if(requestCode == UPDATE_CODE && resultCode == RESULT_OK) {

            Note note = new Note(data.getStringExtra("note_id"),data.getStringExtra("note_updated"));
            noteViewModel.update(note);
            print("Updated");
        }else{
               print("Cancel");
            }

    }

    private void print(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"Owner OnStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"Owner OnPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"Owner OnResume");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Owner OnDestroy");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"Owner OnStop");
    }

    @Override
    public void OnDeleteClickListener(Note mnote) {

        noteViewModel.delete(mnote);
    }
}
