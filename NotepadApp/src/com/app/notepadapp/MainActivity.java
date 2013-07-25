package com.app.notepadapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

//implement the OnClickListener interface
public class MainActivity extends Activity 
	implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        
        //create DB
        DatabaseHandler db = new DatabaseHandler(this);
    	//get the Button reference
    	//Button is a subclass of View 
    	//buttonClick if from main.xml "@+id/addButton"
        View btnClick = findViewById(R.id.addButton);
        //set event listener
        btnClick.setOnClickListener(this);
    }

    //override the OnClickListener interface method
	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.addButton){
			//define a new Intent for the second Activity
			Intent intent = new Intent(this,newNoteActivity.class);
			//start the second Activity
			this.startActivity(intent);
		}
	}
}