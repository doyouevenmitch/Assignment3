package com.app.createdb;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
 
public class MainActivity extends Activity {
 
    SQLiteDatabase myDB = null;
    String TableName = "Student";
    String Data = "";
    EditText studentName;
    EditText studentAge;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
         
        setContentView(R.layout.activity_main);
         
        studentName = (EditText) findViewById(R.id.editText1); // getting text fields (Edit Text)
         
        studentAge= (EditText) findViewById(R.id.editText2);
 
    }
     
    // this method will trigger when user clicked " CreateDB & Table " button 
 
    public void createDB(View v) { 
 
        try {
            // Get the database if database is not exists create new database 
            // Database name is " test " 
             
            myDB = this.openOrCreateDatabase("test", MODE_PRIVATE, null);
             
            // Create table with tow columns (Name and Age)
         
            myDB.execSQL("CREATE TABLE IF NOT EXISTS " + TableName
                    + " (Name VARCHAR, Age INT(3));");
 
            Toast.makeText(getBaseContext(), "Database & Table Created",Toast.LENGTH_LONG).show();
             
        } catch (Exception e) {
 
            Log.e("Error", "Error", e);
 
            Toast.makeText(getBaseContext(),
                    "Error in creating atabase or Table", Toast.LENGTH_LONG).show();
 
        }
 
    }
     
    // this method will trigger when user clicked " Drop DB " button 
 
    public void drop(View v) {
 
        try {
            // we use deleteDatabse("Database name ") method to delete database 
            // we have to pass String parameter to give the name of database
             
            deleteDatabase("test"); 
 
            Toast.makeText(getBaseContext(), "Databse Deleted", Toast.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
 
            Log.e("Error", "Error", e);
 
            Toast.makeText(getBaseContext(), "Error in deleting",Toast.LENGTH_LONG).show();
 
        }
 
    }
 
    // this method will trigger when user clicked " Save Data " button 
 
     
    public void addData(View v) {
         
        try{
             
            // getting created database or if database is not exists create new database
             
        myDB = this.openOrCreateDatabase("test", MODE_PRIVATE, null);
 
 
        String name =studentName.getText().toString();
        int age = Integer.parseInt(studentAge.getText().toString().trim());
 
        myDB.execSQL("INSERT INTO " + TableName + " (Name, Age)" + " VALUES ('"
                + name + "', " + age + ");");
 
        Toast.makeText(getBaseContext(), "Date Saved ", Toast.LENGTH_LONG).show();
        }
         
        catch(Exception e){
             
            Log.e("Error", "Error", e);
             
            Toast.makeText(getBaseContext(), "No Database found   ", Toast.LENGTH_LONG).show();
             
        }
    }
 
    // this method will trigger when user clicked " View Data " button 
 
    public void viewData(View v) {
         
        // creating new intent using ViewActivity Class and start activity to show table data
 
        Intent i = new Intent(this,ViewActivity.class);
        startActivity(i);
 
    }
 
}