package com.intel.shanghai.yamba;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StatusActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//display the screen layout
		setContentView(R.layout.activity_status);
		
		// set proxy in case you are behind one
		// System.setProperty("http.proxyHost", "proxy here");
		// System.setProperty("http.proxyPort", "port here");
	}
	
	//handle the click on "Post update"
	public void postTwitterUpdate (View v)
	{
		//TODO post an update to Twitter
		Toast.makeText(this, "we are updating ...", Toast.LENGTH_LONG).show();
		
		//use the yamba library to post a twitt
		try {
			YambaClient client = new YambaClient("student", "password");
			client.postStatus("Marius is testing");
		} catch (YambaClientException e) {
			e.printStackTrace();
		}
	}	
}
