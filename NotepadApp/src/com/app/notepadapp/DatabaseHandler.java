    package com.app.notepadapp;

    import java.util.ArrayList;
    import java.util.List;

    import android.content.SharedPreferences;

    public class DatabaseHandler {

    private SharedPreferences NOTES_DB;
    private SharedPreferences.Editor NOTES_DB_EDITOR;
    private int NOTES_COUNT;

	public DatabaseHandler(SharedPreferences db) {

        NOTES_DB = db;
        NOTES_DB_EDITOR = NOTES_DB.edit();
        NOTES_COUNT = NOTES_DB.getInt("notesCount", 0);

	}

	// Adding new note
	void addNote(ContentHandler note) {

        int noteID = note.getID();

        NOTES_DB_EDITOR.putInt("notesCount", NOTES_COUNT + 1);
        NOTES_DB_EDITOR.putString("noteTitle_" + noteID, note.getTitle());
        NOTES_DB_EDITOR.putString("noteContent_" + noteID, note.getContent());
        NOTES_DB_EDITOR.putString("noteDateCreated_" + noteID, note.getCreated());
        NOTES_DB_EDITOR.putString("noteDateModified_" + noteID, note.getModified());

        NOTES_DB_EDITOR.commit();

	}

	// Getting single contact
	public ContentHandler getNote(int id) {

        ContentHandler note = new ContentHandler();

        note.setID(id);
        note.setTitle(NOTES_DB.getString("noteTitle_" + id, "Sample Name"));
        note.setContent(NOTES_DB.getString("noteContent_" + id, "Sample Content"));
        note.setCreated(NOTES_DB.getString("noteDateCreated_" + id, "00.00.00"));
        note.setModified(NOTES_DB.getString("noteDateModified_" + id, "00.00.00"));

		// return note
		return note;
	}
	
	// Getting All Contacts
	public List<ContentHandler> getAllNotes() {

        List<ContentHandler> noteList = new ArrayList<ContentHandler>();

		// looping through all rows and adding to list
		for (int i = 0; i < NOTES_COUNT; i++){

            ContentHandler note = new ContentHandler();

            note.setID(i);
            note.setTitle(NOTES_DB.getString("noteTitle_" + i, "Sample Name"));
            note.setContent(NOTES_DB.getString("noteContent_" + i, "Sample Content"));
            note.setCreated(NOTES_DB.getString("noteDateCreated_" + i, "00.00.00"));
            note.setModified(NOTES_DB.getString("noteDateModified_" + i, "00.00.00"));

            if (NOTES_DB.getBoolean("noteActive_" + i,true)){
                noteList.add(note);
            }

        }

		return noteList;
	}

	// Updating single note
    void updateNote(ContentHandler note) {

        int noteID = note.getID();

        NOTES_DB_EDITOR.putString("noteTitle_" + noteID, note.getTitle());
        NOTES_DB_EDITOR.putString("noteContent_" + noteID, note.getContent());
        NOTES_DB_EDITOR.putString("noteDateCreated_" + noteID, note.getCreated());
        NOTES_DB_EDITOR.putString("noteDateModified_" + noteID, note.getModified());

        NOTES_DB_EDITOR.commit();

    }

	// Deleting single note
	public void deleteNote(int id) {

        NOTES_DB_EDITOR.putBoolean("noteActive_" + id, false);

        NOTES_DB_EDITOR.commit();

	}


	// Getting notes Count
	public int getNotesCount() {
		return NOTES_COUNT;
	}

}