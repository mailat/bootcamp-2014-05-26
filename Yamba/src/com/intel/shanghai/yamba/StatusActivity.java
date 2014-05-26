package com.intel.shanghai.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StatusActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//display the screen layout
		setContentView(R.layout.activity_status);
		
		//do an automatic redirection to second activity
		startActivity(new Intent(this, SecondActivity.class));
		
	}
}
