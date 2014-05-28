package com.intel.shanghai.yamba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	
	static final int DELAY = 60000; // 1 minute delay bewteen the get timeline operation
	private boolean runFlag  = false; // state of the running service operation
	private Updater updater;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Yamba", "Service onCreate");
		
		//at the beginning we create a thread for the update
		updater = new Updater();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d("Yamba", "Service onStartCommand");
		
		//check if we already have a running thread
		if (!this.runFlag)
		{
			this.runFlag = true;
			this.updater.start();
		}		
		
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("Yamba", "Service onDestroy");
		
		//try to force-closed the updater if is still running
		this.runFlag = false;
		this.updater.interrupt();
		this.updater = null;
	}
	
	private class Updater extends Thread
	{

		public Updater ()
		{
			super("UpdaterService-Updater");
		}
		
		@Override
		public void run() {
			// information if service is running
			UpdaterService updaterService = UpdaterService.this;
			while (updaterService.runFlag)
			{
				try {
					//TODO go wild and get data from server
					//Yamba get ...
					
					//delay sleep between data fetch
					Thread.sleep(DELAY);
				} catch (Throwable e) {
					e.printStackTrace();
					updaterService.runFlag = false;
				}
			}

		}//end updater
		
	}

}
