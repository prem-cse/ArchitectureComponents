package com.example.architecturecomponents.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.architecturecomponents.R;

public class NewNoteActivity extends AppCompatActivity {

    private static final String TAG = "NewNoteActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        final EditText editText = findViewById(R.id.noteEdittext);
        Button button = findViewById(R.id.noteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if(editText.getText().toString().equals("")){
                    setResult(RESULT_CANCELED,intent);
                }else{
                    intent.putExtra("note_added",editText.getText().toString());
                    setResult(RESULT_OK,intent);
                }
                finish();
            }
        });


    }
}
