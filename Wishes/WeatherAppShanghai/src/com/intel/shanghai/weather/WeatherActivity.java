package com.intel.shanghai.weather;


import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity {
	String request;
	String response;
	TextView listText;
	TextView minTemp;
	TextView maxtemp;
	ImageView imageIcon;

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
		minTemp = (TextView) findViewById(R.id.minTemp);
		maxtemp = (TextView) findViewById(R.id.maxTemp);
		imageIcon = (ImageView) findViewById(R.id.imageIcon);
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
				minTemp.setText(new Float(jsonObj.getString("temp_min")).intValue() + "\u00B0");
				maxtemp.setText(new Float(jsonObj.getString("temp_max")).intValue() + "\u00B0");

				//TODO - use a Thread to load this image
				URL newurl;
				try {
					JSONArray jArray = jObj.getJSONArray("weather");
					JSONObject jObject = jArray.getJSONObject(0);
					String icon = jObject.getString("icon");
					newurl = new URL("http://openweathermap.org/img/w/" + icon +".png");
					Bitmap iconBitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); 
					imageIcon.setImageBitmap(iconBitmap);
					
				} catch (Throwable e) {
					e.printStackTrace();
				}

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
