/**
 * This class is part of the Noteapp android application, providing basic note-taking functionality.
 * This application is an assignment 3 of the Mobility subject in Bond University.
 *
 * Developed by "The Alan Turings" group.
 */

package com.app.notepadapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.widget.Toast;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity
	implements OnClickListener {
    /**
     * This class describes main application activity of the Noteapp
     * and in order to do so - extends Activity class.
     *
     * Class implements following methods overriding activity:
     *  - @Override onCreate()   - called, when activity is created. Provides content layout and listeners.
     *  - buildList()            - notes list builder. Take sorting method as input parameter
     *  - @Override onClick()    - onClick listen for main activity buttons ("New note", "Sort")
     *
     */

    // Declaring variables
    private TableLayout NOTES_LIST;
    private int NOTES_COUNT = 0;
    private DatabaseHandler DB_HANDLER;
    private static final String DB_NAME = "MyNotes";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**
         * This method is called when Main activity is created.
         * Implement button onClick listeners and define variables.
         */

        super.onCreate(savedInstanceState);

        // Set activity to run full screen and don't show application name
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set content view to the mail.xml layout
        setContentView(R.layout.main);

        // Connect onClickListener implemented in this class to buttons with ids "addButton" and "sortButton"
        findViewById(R.id.addButton).setOnClickListener(this);
        findViewById(R.id.sortButton).setOnClickListener(this);

        // Assign link to the notes list for further use
        NOTES_LIST = (TableLayout) findViewById(R.id.notesList);

        // Get SharedPreferences database and feed it to Database Handler to control later on.
        SharedPreferences NOTES_DB = getSharedPreferences(DB_NAME, 0);
        DB_HANDLER = new DatabaseHandler(NOTES_DB);

        // Set notes count
        NOTES_COUNT = DB_HANDLER.getNotesCount();

        // Build list of notes using sorting method in database. If there is none - "normal" is used.
        buildList(DB_HANDLER.getSortMethod());

    }


    private void buildList(String sortMethod) {
        /**
         * This method build up and sort list of notes in order accepted as a parameter.
         * There are 7 different types to sort array implemented:
         *  - By modify date                - "modifyDate"
         *  - By modify date in back order  - "modifyDate_back"
         *  - By create date                - "createDate"
         *  - By create date in back order  - "createDate_back"
         *  - By alphabet                   - "alphabet"
         *  - By alphabet in back order     - "alphabet_back"
         *  - Normal mode                   - "normal" // Used, when non of users used. Represents unsorted array of notes in the order of ID.
         *
         *  Every sorting method implemented as Bubble-sort algorithm inside if-statement corresponding to sorting type.
         *
         */

        // Get list of all notes from database
        List<ContentHandler> notesList =  DB_HANDLER.getAllNotes();

        // If sorting by Modify Date:
        if (sortMethod.equals("modifyDate")){

            int flag = -1;
            // Until no pairs were swapped inside array
            while (flag != 0) {
                flag = 0;
                // Iterate over list and swap pairs according the given rule
                for (int i = 0; i < notesList.size() - 1; i++ ){
                    // Compare date of current note and the one after, swap if needed
                    Date thisDate = new Date(notesList.get(i).getModified());
                    Date nextDate = new Date(notesList.get(i+1).getModified());
                    if (thisDate.before(nextDate)){
                        ContentHandler tempNote = notesList.get(i+1);
                        notesList.set(i+1,notesList.get(i));
                        notesList.set(i,tempNote);
                        // If swapped this time - indicate there is a swap
                        flag = 1;
                    }
                }
            }
        }

        // If sorting by Modify Date back:
        if (sortMethod.equals("modifyDate_back")){
            int flag = -1;
            // Until no pairs were swapped inside array
            while (flag != 0) {
                flag = 0;
                // Iterate over list and swap pairs according the given rule
                for (int i = 0; i < notesList.size() - 1; i++ ){
                    Date thisDate = new Date(notesList.get(i).getModified());
                    Date nextDate = new Date(notesList.get(i+1).getModified());
                    if (thisDate.after(nextDate)){
                        ContentHandler tempNote = notesList.get(i+1);
                        notesList.set(i+1,notesList.get(i));
                        notesList.set(i,tempNote);
                        flag = 1;
                    }
                }
            }
        }

        // If sorting by Create Date:
        if (sortMethod.equals("createDate")){
            int flag = -1;
            while (flag != 0) {
                flag = 0;

                for (int i = 0; i < notesList.size() - 1; i++ ){
                    Date thisDate = new Date(notesList.get(i).getCreated());
                    Date nextDate = new Date(notesList.get(i+1).getCreated());
                    if (thisDate.before(nextDate)){
                        ContentHandler tempNote = notesList.get(i+1);
                        notesList.set(i+1,notesList.get(i));
                        notesList.set(i,tempNote);
                        flag = 1;
                    }
                }

            }
        }

        // If sorting by Create date back:
        if (sortMethod.equals("createDate_back")){
            int flag = -1;
            while (flag != 0) {
                flag = 0;

                for (int i = 0; i < notesList.size() - 1; i++ ){
                    Date thisDate = new Date(notesList.get(i).getCreated());
                    Date nextDate = new Date(notesList.get(i+1).getCreated());
                    if (thisDate.after(nextDate)){
                        ContentHandler tempNote = notesList.get(i+1);
                        notesList.set(i+1,notesList.get(i));
                        notesList.set(i,tempNote);
                        flag = 1;
                    }
                }

            }
        }

        // If sorting by alphabet
        if (sortMethod.equals("alphabet")){
            int flag = -1;
            while (flag != 0) {
                flag = 0;

                for (int i = 0; i < notesList.size() - 1; i++ ){
                    String thisTitle = notesList.get(i).getTitle();
                    String nextTitle = notesList.get(i+1).getTitle();

                    if (thisTitle.charAt(0) > nextTitle.charAt(0)){
                        ContentHandler tempNote = notesList.get(i+1);
                        notesList.set(i+1,notesList.get(i));
                        notesList.set(i,tempNote);
                        flag = 1;
                    }
                }

            }
        }

        // If sorted by alphabet back
        if (sortMethod.equals("alphabet_back")){
            int flag = -1;
            while (flag != 0) {
                flag = 0;

                for (int i = 0; i < notesList.size() - 1; i++ ){
                    String thisTitle = notesList.get(i).getTitle();
                    String nextTitle = notesList.get(i+1).getTitle();

                    if (thisTitle.charAt(0) < nextTitle.charAt(0)){
                        ContentHandler tempNote = notesList.get(i+1);
                        notesList.set(i+1,notesList.get(i));
                        notesList.set(i,tempNote);
                        flag = 1;
                    }
                }
            }
        }

        // Build sorted list as buttons inside Notes list
        for ( int i = 0; i < notesList.size(); i++ ){
            // Create new blank button
            Button newNote = new Button(this);
            // Set button ID
            newNote.setId(notesList.get(i).getID());
            // Set button Title
            newNote.setText(notesList.get(i).getTitle());
            // Set this class as onClick listener for this button
            newNote.setOnClickListener(this);
            // Add it to the list
            NOTES_LIST.addView(newNote);
        }

        // Anounce, how many notes user have. Included special options for 1 and 0
        if (notesList.size() == 1){
            Toast.makeText(getBaseContext(), "You have " +  notesList.size() + " note" , Toast.LENGTH_LONG).show();
        } else if (notesList.size() == 0){
            Toast.makeText(getBaseContext(), "You don't have any notes yet" , Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "You have " +  notesList.size() + " notes" , Toast.LENGTH_LONG).show();
        }

    }

	@Override
	public void onClick(View arg0) {
        /**
         * This method implements onClick, which is called when one of the buttons is clicked.
         * As a result - it is separated in three branches by if statements according to the button.
         */

        // If "New note" button clicked
		if (arg0.getId() == R.id.addButton){
            // Create link to the next activity - newNoteActivity
			Intent intent = new Intent(this,newNoteActivity.class);
            // Get current date
            Date date = new Date();
            // Create new note
            ContentHandler note = new ContentHandler(NOTES_COUNT,"New note " + (NOTES_COUNT + 1), "" , date.toString() , date.toString());
            // Add it to the database
            DB_HANDLER.addNote(note);
            // Increase note count
            NOTES_COUNT = NOTES_COUNT + 1;
            // Leave new note's ID as an editNoteID for newNoteActivity
            intent.putExtra("editNoteId",note.getID());
            intent.putExtra("editMode",0);
            // Start newNoteActivity for the new note
            this.startActivity(intent);
        }
        // If sort button clicked
        else if (arg0.getId() == R.id.sortButton){
            // Build new user dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Define sort names
            String button1Label = "Modify date ";
            String button2Label = "Creation date ";
            String button3Label = "Alphabet ";

            // If sort method is already Create - when create sort would be chosen Create Back sort would be used instead. It is indicated by ^ sign for every sort.
            if (DB_HANDLER.getSortMethod().equals("createDate")){
                button2Label += "^";
            }
            if (DB_HANDLER.getSortMethod().equals("modifyDate")){
                button1Label += "^";
            }
            if (DB_HANDLER.getSortMethod().equals("alphabet")){
                button3Label += "^";
            }
            // Set dialog parameters and buttons:
            builder.setTitle("Sort by:")
                    // Add "Modify Date" button to dialog
                    .setNegativeButton(button1Label, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (DB_HANDLER.getSortMethod().equals("modifyDate")){
                                DB_HANDLER.setSortMethod("modifyDate_back");
                            } else {
                                DB_HANDLER.setSortMethod("modifyDate");
                            }
                            // Restart Activity
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    })
                    // Add "Create Date" button to dialog
                    .setNeutralButton(button2Label, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (DB_HANDLER.getSortMethod().equals("createDate")){
                                DB_HANDLER.setSortMethod("createDate_back");
                            } else {
                                DB_HANDLER.setSortMethod("createDate");
                            }
                            // Restart activity
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    })
                    // Add "Alphabet button to dialog
                    .setPositiveButton(button3Label, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (DB_HANDLER.getSortMethod().equals("alphabet")){
                                DB_HANDLER.setSortMethod("alphabet_back");
                            } else {
                                DB_HANDLER.setSortMethod("alphabet");
                            }
                            // Restart activity
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });

            // create new dialog from settings above
            AlertDialog dialog = builder.create();
            // show this dialog
            dialog.show();

        } else {
            // If one of the notes clicked
            // Create newNoteActivity for this note
            Intent intent = new Intent(this,newNoteActivity.class);
            // Leave noteId for next activity to get
            intent.putExtra("editNoteId",arg0.getId());
            // Start next activity
            this.startActivity(intent);
            }
		}
}
