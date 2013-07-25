package com.app.createdb;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
 
public class ViewActivity extends Activity {
     
    SQLiteDatabase myDB = null;
    String TableName = "Student";
    String Data = "";
 
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
         
        try {
 
            myDB = this.openOrCreateDatabase("test", MODE_PRIVATE, null);
             
            //getting the cursor object 
             
            Cursor c = myDB.rawQuery("SELECT * FROM " + TableName, null);
 
            int Column1 = c.getColumnIndex("Name");
            int Column2 = c.getColumnIndex("Age");
 
            c.moveToFirst();
             
            if (c != null) {
 
                do {
                    String Name = c.getString(Column1);
                     
                    int Age = c.getInt(Column2);
                     
                    Data = Data + Name + "\t\t" + Age + "\n\n";
                     
                } while (c.moveToNext());
            }
 
            TextView tv = new TextView(this); // creating Text View to show data in the app
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundResource(R.drawable.b);
            tv.setTextSize(18F);
            tv.setText("\n"+"Name \t| Age \n ----------------------\n"+Data);
            setContentView(tv);  // set created text view as Content View 
             
        }
 
        catch (Exception e) {
             
            Log.e("Error", "Error", e);
             
            Toast.makeText(getBaseContext(), "No Data found   ", Toast.LENGTH_LONG).show();
             
        } finally {
            if (myDB != null)
                myDB.close();
        }
    }
 
     
 
}
