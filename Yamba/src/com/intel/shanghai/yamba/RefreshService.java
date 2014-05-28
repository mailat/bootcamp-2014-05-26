package com.intel.shanghai.yamba;

import java.util.List;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RefreshService extends IntentService {

	public RefreshService() {
		super("RefreshService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("Yamba", "Break is over, get the new posts.");
		
		DBHelper dbhelper = new DBHelper(this);
		SQLiteDatabase db = null;
		try {
			//get the timeline from server
			List<Status> timeline = ((YambaApplication) getApplication()).getYambaClient().getTimeline(20);
			
			//get a db writable reference
			db = dbhelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			 //parse the values
			for (Status status : timeline)
			{
				Log.d("Yamba", status.getUser() + ": " + status.getMessage() + "-" + status.getCreatedAt());		
				//insert the value also in the table timeline
				values.clear();
				values.put(DBHelper.C_USER, status.getUser());
				values.put(DBHelper.C_MESSAGE, status.getMessage());
				values.put(DBHelper.C_CREATED_AT, status.getCreatedAt().getTime());
				db.insertOrThrow(DBHelper.TABLE, null, values);
			}
		} catch (Throwable e) { e.printStackTrace(); }
		finally
		{ 
			if (db != null )
				db.close(); }
	}

}
