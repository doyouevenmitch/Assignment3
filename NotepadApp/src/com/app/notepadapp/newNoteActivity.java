package com.app.notepadapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class newNoteActivity extends Activity implements View.OnClickListener {
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.newnote);

        int editNote;

        Bundle extras = getIntent().getExtras();

        editNote = extras.getInt("editNoteId");




        View btnClick = findViewById(R.id.saveNewNote);
        btnClick.setOnClickListener(this);

        Toast.makeText(getBaseContext(), "Editing Note: " + editNote, Toast.LENGTH_LONG).show();

	}

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.saveNewNote){
            //define a new Intent for the second Activity
            Intent intent = new Intent(this,MainActivity.class);
            //start the second Activity
            this.startActivity(intent);


        }
    }

}
