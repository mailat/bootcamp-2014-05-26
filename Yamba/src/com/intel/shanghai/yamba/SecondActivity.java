package com.intel.shanghai.yamba;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Dummy class for testing dummy code
 * @author mailat
 *
 */
public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_status);
		Log.d("Yamba", "SecondActivty - onCreate");
		
		//DUMMY code for showing layout creating in code 
		EditText editText = new EditText(this);
		LayoutParams layoutParams = new LayoutParams(230, 20);
		editText.setLayoutParams(layoutParams);
		((LinearLayout) findViewById(R.id.mainView)).addView(editText);
		
		//DUMMY code for

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	

}
