package com.example.databasedemo;

import android.os.Bundle;
import android.app.Activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener 
{
    /** Called when the activity is first created. */
    EditText etno,etname;
    Button btnSave;
    DatabaseAdapter dbAdapter;
 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAdapter=new DatabaseAdapter(getApplicationContext());
        
        etno=(EditText)findViewById(R.id.txtNo);
        etname=(EditText)findViewById(R.id.txtName);
        btnSave=(Button)findViewById(R.id.Save);
        btnSave.setOnClickListener(this);
    }
 @Override
 public void onClick(View v) {
  // TODO Auto-generated method stub
  if(v.getId()==R.id.Save)
  {
  //Toast.makeText(getApplicationContext(), "save",Toast.LENGTH_LONG).show();
   String no=etno.getText().toString();
   String name=etname.getText().toString();
   dbAdapter.open();
  long inserted=dbAdapter.insertTest(no,name);
   if(inserted>=0)
   {
 Toast.makeText(getApplicationContext(), "data saved",Toast.LENGTH_LONG).show();
    
    etno.setText("");
    etname.setText("");
   }
   else
   {
 Toast.makeText(getApplicationContext(), "data not saved",Toast.LENGTH_LONG).show();
    
   }
   dbAdapter.close();
  }
 }

}

