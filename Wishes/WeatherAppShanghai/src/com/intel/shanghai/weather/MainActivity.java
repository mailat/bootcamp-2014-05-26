package com.intel.shanghai.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	String city;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//(EditText) findby

	}
	
	public void jumpToForecast (View v)
	{
		city = ( (EditText) findViewById(R.id.editText)).getText().toString();
		
		Intent intent = new Intent(this, ForecastActivity.class);
		intent.putExtra("city", city);
		startActivity(intent);
	}
	
	public void jumpToWeather (View v)
	{
		city = ( (EditText) findViewById(R.id.editText)).getText().toString();
		
		Intent intent = new Intent(this, WeatherActivity.class);
		intent.putExtra("city", city);
		startActivity(intent);
	}
}
