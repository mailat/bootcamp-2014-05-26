package com.intel.shanghai.weather;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity {
	String request;
	String response;
	TextView listText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);

		// get the passed values from the MainActivty and prepare tge
		Intent intent = getIntent();
		String city = intent.getStringExtra("city");
		city = city.replaceAll(" ", "%20");
		request = "http://api.openweathermap.org/data/2.5/weather?q=" + city
				+ ",en&units=metric";

		listText = (TextView) findViewById(R.id.listText);

		// set the proxy in case you need it
		// System.setProperty("http.proxyHost", "proxy here");
		// System.setProperty("http.proxyPort", "port here");

		//get the data in an AsyncTask
		new WeatherUpdater().execute();

	}

	class WeatherUpdater extends AsyncTask<String, Integer, String> {
		private ProgressDialog progress;

		@Override
		protected String doInBackground(String... statuses) {
			try {
				// transporter for our in->out call
				HttpClient client = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(request);
				response = client.execute(httpget, new BasicResponseHandler());
				return response;

			} catch (Throwable e) {
				Log.d("Weather", e.getMessage());
				return "Error on posting" + e.getMessage();
			}
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();

			//parse the JSON and get the temperature
			try {
				JSONObject jObj = new JSONObject(response);
				JSONObject jsonObj = jObj.getJSONObject("main");
				listText.setText(new Float(jsonObj.getString("temp")).intValue() + "\u00B0");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(WeatherActivity.this,
					"Get the weather", "Please wait ....");
		}

	}

}
