package com.intel.shanghai.weather;

import java.io.InputStream;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

		// get the data in an AsyncTask
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

			// parse the JSON and get the temperature
			try {
				JSONObject jObj = new JSONObject(response);
				JSONObject jsonObj = jObj.getJSONObject("main");
				listText.setText(new Float(jsonObj.getString("temp"))
						.intValue() + "\u00B0");
				minTemp.setText(new Float(jsonObj.getString("temp_min"))
						.intValue() + "\u00B0");
				maxtemp.setText(new Float(jsonObj.getString("temp_max"))
						.intValue() + "\u00B0");

				// use a Thread to load this image
				JSONArray jArray = jObj.getJSONArray("weather");
				JSONObject jObject = jArray.getJSONObject(0);
				String url = "http://openweathermap.org/img/w/"
						+ jObject.getString("icon") + ".png";
				Log.d("Weather", "http://openweathermap.org/img/w/" + url + ".png");
				new DownloadImageTask(imageIcon).execute(url);

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

	public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
			bmImage.setVisibility(View.VISIBLE);
			super.onPostExecute(result);

		}

	}

}
