package com.app.notepadapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.widget.Toast;

import java.util.ArrayList;
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

        findViewById(R.id.addButton).setOnClickListener(this);
        findViewById(R.id.sortButton).setOnClickListener(this);

        NOTES_LIST = (TableLayout) findViewById(R.id.notesList);

        NOTES_DB = getSharedPreferences(DB_NAME, 0);
        DB_HANDLER = new DatabaseHandler(NOTES_DB);
        NOTES_COUNT = DB_HANDLER.getNotesCount();

        buildList(DB_HANDLER.getSortMethod());

    }


    private void buildList(String sortMethod) {

        List<ContentHandler> notesList =  DB_HANDLER.getAllNotes();

        if (sortMethod.equals("modifyDate")){

            int flag = -1;
            while (flag != 0) {
                flag = 0;

                for (int i = 0; i < notesList.size() - 1; i++ ){
                    Date thisDate = new Date(notesList.get(i).getModified());
                    Date nextDate = new Date(notesList.get(i+1).getModified());
                    if (thisDate.before(nextDate)){
                        ContentHandler tempNote = notesList.get(i+1);
                        notesList.set(i+1,notesList.get(i));
                        notesList.set(i,tempNote);
                        flag = 1;
                    }
                }

            }
        }

        if (sortMethod.equals("normal")){
            //notesSortedList = notesList;
        }

        if (sortMethod.equals("modifyDate_back")){
            int flag = -1;
            while (flag != 0) {
                flag = 0;

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

        //notesList = notesSortedList;

        for(int i = 0; i < notesList.size(); i ++){
            Button newNote = new Button(this);
            newNote.setId(notesList.get(i).getID());
            newNote.setText(notesList.get(i).getTitle());
            newNote.setOnClickListener(this);
            NOTES_LIST.addView(newNote);
        }


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


            } else if (arg0.getId() == R.id.sortButton){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                String button1Label = "Modify date ";
                String button2Label = "Creation date ";
                String button3Label = "Alphabet ";

                if (DB_HANDLER.getSortMethod().equals("createDate")){
                    button2Label += "^";
                }
                if (DB_HANDLER.getSortMethod().equals("modifyDate")){
                    button1Label += "^";
                }
                if (DB_HANDLER.getSortMethod().equals("alphabet")){
                    button3Label += "^";
                }


                builder.setTitle("Sort by:")

                        .setNegativeButton(button1Label, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (DB_HANDLER.getSortMethod().equals("modifyDate")){
                                    DB_HANDLER.setSortMethod("modifyDate_back");
                                } else {
                                    DB_HANDLER.setSortMethod("modifyDate");
                                }

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        })

                        .setNeutralButton(button2Label, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (DB_HANDLER.getSortMethod().equals("createDate")){
                                    DB_HANDLER.setSortMethod("createDate_back");
                                } else {
                                    DB_HANDLER.setSortMethod("createDate");
                                }

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        })

                        .setPositiveButton(button3Label, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (DB_HANDLER.getSortMethod().equals("alphabet")){
                                    DB_HANDLER.setSortMethod("alphabet_back");
                                } else {
                                    DB_HANDLER.setSortMethod("alphabet");
                                }

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();


            } else {

            Intent intent = new Intent(this,newNoteActivity.class);
            intent.putExtra("editNoteId",arg0.getId());
            intent.putExtra("editMode", 1);

            this.startActivity(intent);

            }

		}


}
