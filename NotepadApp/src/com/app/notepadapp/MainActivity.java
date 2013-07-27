package com.app.notepadapp;

import android.app.Activity;
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

    private TableLayout NOTES_LIST;
    private int NOTES_COUNT = 0;
    private SharedPreferences NOTES_DB;
    private DatabaseHandler DB_HANDLER;
    private static final String DB_NAME = "MyNotes";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        View btnClick = findViewById(R.id.addButton);
        btnClick.setOnClickListener(this);

        NOTES_LIST = (TableLayout) findViewById(R.id.notesList);

        NOTES_DB = getSharedPreferences(DB_NAME, 0);
        DB_HANDLER = new DatabaseHandler(NOTES_DB);
        NOTES_COUNT = DB_HANDLER.getNotesCount();

        buildList();

        Toast.makeText(getBaseContext(), "Notes: " +  NOTES_COUNT , Toast.LENGTH_LONG).show();

    }

    private void buildList() {

        List<ContentHandler> notesList =  DB_HANDLER.getAllNotes();

        for(int i = 0; i < NOTES_COUNT; i ++){

            Button newNote = new Button(this);

            newNote.setId(notesList.get(i).getID());
            newNote.setText(notesList.get(i).getTitle());

            newNote.setOnClickListener(this);

            NOTES_LIST.addView(newNote);

        }
    }

	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.addButton){

			Intent intent = new Intent(this,newNoteActivity.class);

            Button newNote = new Button(this);
            newNote.setId(NOTES_COUNT);
            newNote.setOnClickListener(this);

            Date date = new Date();
            ContentHandler note = new ContentHandler(NOTES_COUNT,"New note " + (NOTES_COUNT + 1), "" , date.toString() , date.toString());
            DB_HANDLER.addNote(note);

            NOTES_COUNT = NOTES_COUNT + 1;

            intent.putExtra("editNoteId",note.getID());
            intent.putExtra("editMode",0);

            this.startActivity(intent);


            } else {

            Intent intent = new Intent(this,newNoteActivity.class);
            intent.putExtra("editNoteId",arg0.getId());
            intent.putExtra("editMode",1);

            this.startActivity(intent);

            }

		}


}
