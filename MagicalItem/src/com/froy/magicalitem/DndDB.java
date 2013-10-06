package com.froy.magicalitem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DndDB extends SQLiteOpenHelper {
	
	private SQLiteDatabase mDb;
	
	public DndDB(Context context) {
		super(context, MyConstants.DB_NAME, null, 1);
		String myPath = MyConstants.DB_PATH+MyConstants.DB_NAME;
		mDb = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READWRITE);
		mDb.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
}
