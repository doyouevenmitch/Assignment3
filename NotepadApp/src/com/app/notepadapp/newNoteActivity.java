package com.app.notepadapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

public class newNoteActivity extends Activity
        implements View.OnClickListener {

    private SharedPreferences NOTES_DB;
    private SharedPreferences.Editor NOTES_DB_EDITOR;
    private DatabaseHandler DB_HANDLER;
    private ContentHandler NOTE;
    private static final String DB_NAME = "MyNotes";



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.newnote);

        int editNoteID;
        EditText noteTitle = (EditText) findViewById(R.id.NoteTitle);
        EditText noteContent = (EditText) findViewById(R.id.NoteContent);

        //DatabaseHandler db = new DatabaseHandler(this);
        NOTES_DB = getSharedPreferences(DB_NAME, 0);
        DB_HANDLER = new DatabaseHandler(NOTES_DB);

        Bundle extras = getIntent().getExtras();
        editNoteID = extras.getInt("editNoteId");

        ContentHandler NOTE =  DB_HANDLER.getNote(editNoteID);

        findViewById(R.id.saveNote).setOnClickListener(this);
        findViewById(R.id.backNote).setOnClickListener(this);
        findViewById(R.id.deleteNote).setOnClickListener(this);

        noteTitle.setText(NOTE.getTitle());
        noteContent.setText(NOTE.getContent());
        noteContent.requestFocus();

        //Toast.makeText(getBaseContext(), editNote.getTitle() , Toast.LENGTH_LONG).show();

	}

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.saveNote){
            //define a new Intent for the second Activity
            Intent intent = new Intent(this,MainActivity.class);


            //start the second Activity
            this.startActivity(intent);
        }

        if(arg0.getId() == R.id.backNote){
            //define a new Intent for the second Activity
            Intent intent = new Intent(this,MainActivity.class);

            //DB_HANDLER.updateNote(NOTE);
            //start the second Activity
            this.startActivity(intent);
        }

        if(arg0.getId() == R.id.deleteNote){

            //DB_HANDLER.deleteNote(NOTE.getID());
            Toast.makeText(getBaseContext(), "Deleted" , Toast.LENGTH_LONG).show();

            //TableLayout noteEditPanel = (TableLayout) findViewById(R.id.editPanel);
            //noteEditPanel.setVisibility(1);

        }


    }

}
