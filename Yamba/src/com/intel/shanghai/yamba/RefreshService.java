package com.intel.shanghai.yamba;

import java.util.List;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService {

	public RefreshService() {
		super("RefreshService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("Yamba", "Break is over, get the new posts.");
		
		try {
			List<Status> timeline = ((YambaApplication) getApplication()).getYambaClient().getTimeline(20);
			
			 //parse the values
			for (Status status : timeline)
				Log.d("Yamba", status.getUser() + ": " + status.getMessage() +
						"-" + status.getCreatedAt());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
