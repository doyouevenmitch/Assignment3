/**
 * This class is part of the Noteapp android application, providing basic note-taking functionality.
 * This application is an assignment 3 of the Mobility subject in Bond University.
 *
 * Developed by "The Alan Turings" group.
 */

package com.app.notepadapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class newNoteActivity extends Activity
        implements View.OnClickListener {
    /**
     * This class describes newNoteActivity the Noteapp android application
     * and in order to do so - extends Activity class.
     * This class allow user to edit or delete single note, which id should be given to this activity
     * from previous one.
     *
     * Class implements following methods overriding activity:
     *  - @Override onCreate()   - called, when activity is created. Provides content layout and listeners.
     *  - @Override onClick()    - onClick listen for main activity buttons ("Delete", "Back", "Save")
     *
     */
    // Declare variables
    private SharedPreferences NOTES_DB;
    private DatabaseHandler DB_HANDLER;
    private int NOTE_ID;
    private static final String DB_NAME = "MyNotes";

	@Override
	public void onCreate(Bundle savedInstanceState) {
        /**
         * This method is called when activity is created.
         * Implement button onClick listeners and define variables.
         */

	    super.onCreate(savedInstanceState);
        // Set activity to run full screen and don't show application name
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Set content view to the newnote.xml layout
        this.setContentView(R.layout.newnote);
        // Link note title object and note content object for further interaction
        EditText noteTitle = (EditText) findViewById(R.id.NoteTitle);
        EditText noteContent = (EditText) findViewById(R.id.NoteContent);
        // Feed SharedPreferences database to the DatabaseHandler to operate it
        NOTES_DB = getSharedPreferences(DB_NAME, 0);
        DB_HANDLER = new DatabaseHandler(NOTES_DB);
        // Get the editNoteID form previous activity
        Bundle extras = getIntent().getExtras();
        NOTE_ID = extras.getInt("editNoteId");
        // Retrieve single note from the database in order to edit it
        ContentHandler NOTE =  DB_HANDLER.getNote(NOTE_ID);
        // Set this class as onClick listener for buttons
        findViewById(R.id.saveNote).setOnClickListener(this);
        findViewById(R.id.backNote).setOnClickListener(this);
        findViewById(R.id.deleteNote).setOnClickListener(this);
        // Fill Title and Content objects with note data
        noteTitle.setText(NOTE.getTitle());
        noteContent.setText(NOTE.getContent());
        // Set focus for note content area
        noteContent.requestFocus();
	}

    @Override
    public void onClick(View arg0) {
        /**
         * This method implements onClick, which is called when one of the buttons is clicked.
         * As a result - it is separated in three branches by if statements according to the button.
         */
        // If save button is clicked
        if(arg0.getId() == R.id.saveNote){
            // Note content and title areas are linked for further use
            EditText noteTitle = (EditText) findViewById(R.id.NoteTitle);
            EditText noteContent = (EditText) findViewById(R.id.NoteContent);
            // Update note's content and title in the database
            DB_HANDLER.updateNote(NOTE_ID, noteTitle.getText().toString() , noteContent.getText().toString());
            // Show message, that note is saved
            Toast.makeText(getBaseContext(), noteTitle.getText().toString() + " has been saved"  , Toast.LENGTH_LONG).show();
        }
        // If back button is clicked
        if(arg0.getId() == R.id.backNote){
            // Create new main activity
            Intent intent = new Intent(this,MainActivity.class);
            // Go back to main activity with no saving
            this.startActivity(intent);
        }
        // If delete button is clicked
        if(arg0.getId() == R.id.deleteNote){
            // Mark given note as deleted
            DB_HANDLER.deleteNote(NOTE_ID);
            // Move back to main activity
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
        }
    }
}
