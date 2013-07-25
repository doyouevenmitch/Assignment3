package com.example.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {
SQLiteDatabase database;
DatabaseOpenHelper dbHelper;
 
 public DatabaseAdapter(Context context)
 {
       dbHelper=new DatabaseOpenHelper(context);
       
 }
 public void open()
 {
  database=dbHelper.getWritableDatabase();
 }
 public void close()
 {
  database.close();
 }
 public long insertTest(String no,String name)
 {
  ContentValues values=new ContentValues();
  values.put("no", no);
  values.put("name", name);
  
  return database.insert("test",null, values);
 }
}