package com.trekguy.crossfitlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "crossfitlog.db";
	
	private static final String CREATE_EXCERSISES = "CREATE TABLE Excersises (Name TEXT, Unit TEXT);";
	private static final String CREATE_WOD = "CREATE TABLE Wod (DayNum INTEGER, Name TEXT);"; // TODO Add Date
	private static final String CREATE_WOD_EXCERSISE = "CREATE TABLE WodExcersise (DayNum INTEGER, GroupNum INTEGER, " +
		"Reps INTEGER, Time INTEGER, Excersise TEXT, Mod TEXT, Unit TEXT);";
	private static final String CREATE_GROUP = "CREATE TABLE WodGroup (DayNum INTEGER, GroupNum INTEGER, Reps INTEGER, Time INTEGER);";
	
	private SQLiteDatabase db;
	
	public DataHelper(Context context)
	{	
		DatabaseHelper dataHelp = new DatabaseHelper(context);
		
		try
		{
			this.db = dataHelp.getWritableDatabase();
		} catch (SQLiteException e)
		{
			//Log.e("Error: ", e.getMessage());
		}
	}
	
	public void addWod (int dayNum, String name)
	{
		ContentValues values = new ContentValues();
		
		values.put("DayNum", dayNum);
		values.put("Name", name);
	
		db.insert("Wod", null, values);
	}
	
	public String[] getWods()
	{
		// TODO add date function
		Cursor cursor = db.query("Wod", new String[] {"DayNum"}, null, null, null, null, null);
		
		String[] wodDays = null;
		
		if (cursor.moveToFirst())
		{	
			wodDays = new String[cursor.getCount()];
			
			for (int i = 0; i<cursor.getCount(); i++)
			{
				wodDays[i] = "Day " + cursor.getString(0);
				
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		
		return wodDays;
	}
	
	public void closeDatabase()
	{
		db.close();
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
	
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_EXCERSISES);
			db.execSQL(CREATE_WOD);
			db.execSQL(CREATE_WOD_EXCERSISE);
			db.execSQL(CREATE_GROUP);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			 //Log.w("Example", "Upgrading database, this will drop tables and recreate.");
			 //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			 onCreate(db);
		}
	}
}
