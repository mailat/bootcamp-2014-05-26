package com.intel.shanghai.yamba;

import com.marakana.android.yamba.clientlib.YambaClient;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApplication extends Application implements OnSharedPreferenceChangeListener  {
	SharedPreferences prefs;
	YambaClient client = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// System.setProperty("http.proxyHost", "proxy here");
		// System.setProperty("http.proxyPort", "port here");
		
		//we get a reference to the SharedPreferences
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.prefs.registerOnSharedPreferenceChangeListener(this);
		
		Log.d("Yamba","YambaApplication-onCreate");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.d("Yamba","YambaApplication-onTerminate");
	}
	
	public synchronized YambaClient getYambaClient() {
		if (this.client == null){
			String username = this.prefs.getString("username", "");
			String password = this.prefs.getString("password", "");
			this.client = new YambaClient(username, password);
		}
		
		return this.client;
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
		this.client = null;
	}
}
