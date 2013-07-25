package com.app.notepadapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "notesDB";

	// Contacts table name
	private static final String TABLE_NOTES = "notes";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_CONTENT = "content";
	private static final String KEY_CREATED = "created";
	private static final String KEY_MODIFIED = "modified";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
				+ KEY_CONTENT + " TEXT,"
				+ KEY_CREATED + " TEXT,"
				+ KEY_MODIFIED + " TEXT" + ")";
		db.execSQL(CREATE_NOTES_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new note
	void addNote(ContentHandler note) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, note.getTitle()); // Note Title
		values.put(KEY_CONTENT, note.getContent()); // Note Content
		values.put(KEY_CREATED, note.getCreated()); // Note Created
		values.put(KEY_MODIFIED, note.getModified()); // Note Modified

		// Inserting Row
		db.insert(TABLE_NOTES, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	ContentHandler getNote(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID,
				KEY_TITLE, KEY_CONTENT, KEY_CREATED, KEY_MODIFIED }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ContentHandler note = new ContentHandler(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
		// return note
		return note;
	}
	
	// Getting All Contacts
	public List<ContentHandler> getAllNotes() {
		List<ContentHandler> noteList = new ArrayList<ContentHandler>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ContentHandler note = new ContentHandler();
				note.setID(Integer.parseInt(cursor.getString(0)));
				note.setTitle(cursor.getString(1));
				note.setContent(cursor.getString(2));
				note.setCreated(cursor.getString(3));
				note.setModified(cursor.getString(4));
				// Adding contact to list
				noteList.add(note);
			} while (cursor.moveToNext());
		}

		// return note list
		return noteList;
	}

	// Updating single note
	public int updateNote(ContentHandler note) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, note.getTitle()); // Note Title
		values.put(KEY_CONTENT, note.getContent()); // Note Content
		values.put(KEY_CREATED, note.getCreated()); // Note Created
		values.put(KEY_MODIFIED, note.getModified()); // Note Modified

		// updating row
		return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(note.getID()) });
	}

	// Deleting single note
	public void deleteNote(ContentHandler note) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NOTES, KEY_ID + " = ?",
				new String[] { String.valueOf(note.getID()) });
		db.close();
	}


	// Getting notes Count
	public int getNotesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NOTES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}