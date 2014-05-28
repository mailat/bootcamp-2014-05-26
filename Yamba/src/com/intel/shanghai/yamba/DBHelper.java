package com.intel.shanghai.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	static final String DB_NAME = "timeline.db";
	static final int DB_VERSION = 1;
	
	//define table names and columns
	static final String TABLE = "timeline";
	static final String C_CREATED_AT = "created_at";
	static final String C_MESSAGE = "message";	
	static final String C_USER = "user";
	
	Context context;
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create the DB with 1 table
		String sql = "CREATE TABLE " + TABLE + " ( " + BaseColumns._ID + 
				" INT PRIMARY KEY, " + C_CREATED_AT + " INT, " +
				C_USER + " TEXT, " +
				C_MESSAGE + " TEXT );";
		
		Log.d("Yamba", "DBHelper sql=" + sql);
		db.execSQL(sql);
		Log.d("Yamba", "DBHelper onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//in case we do an upgrade we just drop the old db
		db.execSQL("DROP TABLE IF EXISTS " + TABLE + ";");
		
		//recreate the db
		onCreate(db);
		Log.d("Yamba", "DBHelper onUpgrade");
	}

}
