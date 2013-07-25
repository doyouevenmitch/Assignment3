package com.app.notepadapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.Toast;

//implement the OnClickListener interface
public class MainActivity extends Activity 
	implements OnClickListener {

    private TableLayout NOTES_LIST;
    private int NOTES_COUNT;
    private DatabaseHandler NOTES_DB;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        
        //create DB
        DatabaseHandler db = new DatabaseHandler(this);
        NOTES_DB = db;
    	//get the Button reference
    	//Button is a subclass of View 
    	//buttonClick if from main.xml "@+id/addButton"
        View btnClick = findViewById(R.id.addButton);
        btnClick.setOnClickListener(this);

        TableLayout tl = (TableLayout) findViewById(R.id.notesList);

        NOTES_LIST = tl;

        //set event listener
    }

    //override the OnClickListener interface method
	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.addButton){
			//define a new Intent for the second Activity
			Intent intent = new Intent(this,newNoteActivity.class);

            Button tv = new Button(this);
            tv.setText("Dynamic layouts ftw!");
            tv.setId(NOTES_COUNT);
            tv.setOnClickListener(this);

            ContentHandler note = new ContentHandler(NOTES_COUNT,"New note" + (NOTES_COUNT + 1), "" , "test" , "test");
            NOTES_DB.addNote(note);

            NOTES_LIST.addView(tv);

            //System.out.print("test");

            NOTES_COUNT = NOTES_COUNT + 1;
            //Toast.makeText(getBaseContext(), "Notes: " + NOTES_COUNT, Toast.LENGTH_LONG).show();

            //this.startActivity(intent);

            //Toast.makeText(getBaseContext(), "Clicked " + arg0.getId(), Toast.LENGTH_LONG).show();


                //c.moveToNext();
            } else {

            Intent intent = new Intent(this,newNoteActivity.class);
            intent.putExtra("editNoteId",arg0.getId());
            this.startActivity(intent);

            }

		}


}
