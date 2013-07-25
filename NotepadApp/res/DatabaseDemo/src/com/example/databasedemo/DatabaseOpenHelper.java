package com.example.databasedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {


	 public static final String DBName = "test_database.db";
	 public static final String TABLE_NAME = "test";
	 public static final String TABLE_SQL = "Create Table "+ TABLE_NAME 
	+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
	+"no TEXT, "
	+"name TEXT);";
	 
	 public DatabaseOpenHelper(Context context) {
	  super(context, DBName, null, 1);
	  // TODO Auto-generated constructor stub
	 }


	 @Override
	 public void onCreate(SQLiteDatabase database) {
	  // TODO Auto-generated method stub
	          database.execSQL(TABLE_SQL);
	 }


	 @Override
	 public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	  // TODO Auto-generated method stub


	 }


	}
