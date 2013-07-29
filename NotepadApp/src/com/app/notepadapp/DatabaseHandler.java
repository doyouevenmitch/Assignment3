/**
 * This class is part of the Noteapp android application, providing basic note-taking functionality.
 * This application is an assignment 3 of the Mobility subject in Bond University.
 *
 * Developed by "The Alan Turings" group.
 */

    package com.app.notepadapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.SharedPreferences;

public class DatabaseHandler {
        /**
         *
         * This class provides handling all database operations, and processes them properly.
         * Implements following methods to operate over the storage:
         *  - Constructor method
         *  - setSortMethod() - set Sorting method for the list of notes;
         *  - getSortMethod() - get Sorting method;
         *  - addNote()       - add new note to the database;
         *  - getNote()       - return note in ContentHandler format;
         *  - getAllNotes()   - get list of notes in ContentHandler format;
         *  - updateNote()    - update title, content and modify date for single note in the database;
         *  - deleteNote()    - mark note as deleted;
         *  - getNotesCount() - returns NOTES_COUNT value;
         *
         */

        // Declare variables and constants
    private final SharedPreferences NOTES_DB;
    private final SharedPreferences.Editor NOTES_DB_EDITOR;
    private int NOTES_COUNT;
    private String SORT_METHOD;


    public DatabaseHandler(SharedPreferences db) {
        /**
         * This method is constructor method for Database Handler class.
         * Called when database handler object is created.
         */
        // Assign variables and constants
        NOTES_DB = db;
        NOTES_DB_EDITOR = NOTES_DB.edit();
        NOTES_COUNT = NOTES_DB.getInt("notesCount", 0);
        SORT_METHOD = NOTES_DB.getString("sortMethod", "normal");
	}

    public void setSortMethod(String sortMethod){
        /**
         * This method set sorting method for notes list.
         * Called by sorting dialog in Main Activity.
         */
        SORT_METHOD = sortMethod;
        NOTES_DB_EDITOR.putString("sortMethod", sortMethod);
        NOTES_DB_EDITOR.commit();
    }

    public String getSortMethod(){
        /**
         * This method is getter for SORT_METHOD variable.
         */
        return SORT_METHOD;
    }

	public void addNote(ContentHandler note) {
        /**
         * This method add new note to the database.
         * Called, when "New note" button is clicked.
         */
        int noteID = note.getID();
        NOTES_DB_EDITOR.putInt("notesCount", NOTES_COUNT + 1);
        NOTES_DB_EDITOR.putString("noteTitle_" + noteID, note.getTitle());
        NOTES_DB_EDITOR.putString("noteContent_" + noteID, note.getContent());
        NOTES_DB_EDITOR.putString("noteDateCreated_" + noteID, note.getCreated() );
        NOTES_DB_EDITOR.putString("noteDateModified_" + noteID, note.getModified());
        NOTES_DB_EDITOR.commit();
	}

	public ContentHandler getNote(int id) {
        /**
         * This method returns new ContentHandler with all note data.
         * Literally just returns note from the database.
         */

        // Create new ContentHandler to fill
        ContentHandler note = new ContentHandler();

        // Fill it with content from the database
        note.setID(id);
        note.setTitle(NOTES_DB.getString("noteTitle_" + id, "Sample Name"));
        note.setContent(NOTES_DB.getString("noteContent_" + id, "Sample Content"));
        note.setCreated(NOTES_DB.getString("noteDateCreated_" + id, "00.00.00"));
        note.setModified(NOTES_DB.getString("noteDateModified_" + id, "00.00.00"));

        // Return it.
		return note;
	}
	
	public List<ContentHandler> getAllNotes() {
        /**
         * This method returns new List of all notes in ContentHandler format.
         */

        // Create new list to fill with notes
        List<ContentHandler> noteList = new ArrayList<ContentHandler>();

        // Iterate over the database to fill list with ContentHandlers filled with notes information.
		for (int i = 0; i < NOTES_COUNT; i++){

            // Create new content handler to fill
            ContentHandler note = new ContentHandler();

            // Fill it with note's information
            note.setID(i);
            note.setTitle(NOTES_DB.getString("noteTitle_" + i, "Sample Name"));
            note.setContent(NOTES_DB.getString("noteContent_" + i, "Sample Content"));
            note.setCreated(NOTES_DB.getString("noteDateCreated_" + i, "00.00.00"));
            note.setModified(NOTES_DB.getString("noteDateModified_" + i, "00.00.00"));

            // If it is not marked as deleted - add it to the list.
            if (NOTES_DB.getBoolean("noteActive_" + i,true)){
                noteList.add(note);
            }

        }

        // Return the list of active notes.
		return noteList;
	}

    public void updateNote(int id,String title,String content) {
        /**
         * This method update Content and Title of single note, and change modify date as well.
         */

        // Set ID of note to update
        int noteID = id;
        // Get current date
        Date date = new Date();
        // Update database
        NOTES_DB_EDITOR.putString("noteTitle_" + noteID, title);
        NOTES_DB_EDITOR.putString("noteContent_" + noteID, content);
        NOTES_DB_EDITOR.putString("noteDateModified_" + noteID, date.toString() );
        // Commit changes
        NOTES_DB_EDITOR.commit();
    }

	public void deleteNote(int id) {
        /**
         * This method marks single note as deleted.
         */
        // Mark note as deleted
        NOTES_DB_EDITOR.putBoolean("noteActive_" + id, false);
        // Commit changes
        NOTES_DB_EDITOR.commit();
	}

	public int getNotesCount() {
        /**
         * This method is getter method for NOTES_COUNT variable.
         */
		return NOTES_COUNT;
	}

}